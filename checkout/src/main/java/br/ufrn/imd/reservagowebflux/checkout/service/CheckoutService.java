package br.ufrn.imd.reservagowebflux.checkout.service;

import br.ufrn.imd.reservagowebflux.base.exception.EntityNotFoundException;
import br.ufrn.imd.reservagowebflux.checkout.model.Checkout;
import br.ufrn.imd.reservagowebflux.checkout.model.dto.BookDto;
import br.ufrn.imd.reservagowebflux.checkout.model.dto.CheckoutDto;
import br.ufrn.imd.reservagowebflux.checkout.model.dto.PaymentDto;
import br.ufrn.imd.reservagowebflux.checkout.model.dto.PlaceDto;
import br.ufrn.imd.reservagowebflux.checkout.model.dto.TransactionDto;
import br.ufrn.imd.reservagowebflux.checkout.repository.CheckoutRepository;
import java.time.LocalDateTime;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.LocalCachedMapOptions.ReconnectionStrategy;
import org.redisson.api.LocalCachedMapOptions.SyncStrategy;
import org.redisson.api.RLocalCachedMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class CheckoutService {
	private final CheckoutRepository checkoutRepository;

	private final WebClient.Builder webClientBuilder;

	private RLocalCachedMapReactive<String, Checkout> checkoutMap;

	private RLocalCachedMapReactive<String, CheckoutDto> placeMap;

	@Value("${admin.server.name}")
	private String ADMIN_SERVER_URL;

	@Value("${payment.server.name}")
	private String PAYMENT_SERVER_URL;

	@Autowired
	public CheckoutService(CheckoutRepository checkoutRepository, Builder webClientBuilder, RedissonReactiveClient cacheser) {
		this.checkoutRepository = checkoutRepository;
		this.webClientBuilder = webClientBuilder;
		LocalCachedMapOptions<String, Checkout> checkoutMapOptions = LocalCachedMapOptions.<String, Checkout>defaults()
				.syncStrategy(SyncStrategy.UPDATE) // If data changes, redis update the local cache of others services
				.reconnectionStrategy(ReconnectionStrategy.CLEAR); // If connection fails, local cache is cleaned after reconnection
		this.checkoutMap = cacheser.getLocalCachedMap("/checkout/", new TypedJsonJacksonCodec(String.class, Checkout.class), checkoutMapOptions);

		LocalCachedMapOptions<String, CheckoutDto> placeMapOptions = LocalCachedMapOptions.<String, CheckoutDto>defaults()
				.syncStrategy(SyncStrategy.UPDATE) // If data changes, redis update the local cache of others services
				.reconnectionStrategy(ReconnectionStrategy.CLEAR); // If connection fails, local cache is cleaned after reconnection
		this.placeMap = cacheser.getLocalCachedMap("/place-availbility/", new TypedJsonJacksonCodec(String.class, CheckoutDto.class), placeMapOptions);
	}

	public Mono<Checkout> findById(String id) {
		return this.checkoutMap.get(id)
				.switchIfEmpty(
						this.checkoutRepository.findById(id)
								.switchIfEmpty(Mono.error(new EntityNotFoundException("Entity of id " + String.valueOf(id) + " not found.")))
								.subscribeOn(Schedulers.boundedElastic())
								.flatMap(c -> this.checkoutMap.fastPut(id, c).thenReturn(c))
				);
	}

	public Mono<CheckoutDto> checkAvailability(String placeId) {
		String adminUri = "http://" + ADMIN_SERVER_URL + "/place/" + placeId;
		return this.placeMap.get(placeId)
				.switchIfEmpty(

					webClientBuilder
					.build()
					.get()
					.uri(adminUri)
					.retrieve()
					.onStatus(status -> status != HttpStatus.OK, e -> Mono.error(new EntityNotFoundException("teste")))
					.bodyToMono(PlaceDto.class)
					.map(PlaceDto::maxNumberOfGuests)
					.flatMap(maxNumberOfGuests ->
						this.checkoutRepository.countAllByPlaceIdAndActiveIsTrueAndCheckoutDateGreaterThan(placeId, LocalDateTime.now())
										.map(currentGuests ->  new CheckoutDto(maxNumberOfGuests,
												Math.toIntExact(currentGuests)))
										.switchIfEmpty(Mono.error(new EntityNotFoundException("Error, not found!")))
										.flatMap(checkoutDto -> this.placeMap.fastPut(placeId, checkoutDto).thenReturn(checkoutDto))
						)


					)
				.subscribeOn(Schedulers.boundedElastic());
	}


	public Mono<TransactionDto> bookLocation(String placeId, BookDto bookDto) {
		String performPaymentUri = "http://" + PAYMENT_SERVER_URL + "/transaction/pay";

		return this.checkAvailability(placeId)
				.flatMap(c -> {
					boolean isPlaceAvailable = !c.isFull();
					if(!isPlaceAvailable){
						return Mono.error(new EntityNotFoundException("teste"));
					}

					Checkout checkout = new Checkout();
					checkout.setPlaceId(placeId);
					checkout.setUserId(bookDto.paymentDto().creditCard().ownerId());
					checkout.setCheckoutDate(bookDto.checkoutDate());

					return this.placeMap.fastRemove(placeId).thenReturn(this.checkoutRepository.save(checkout));
				})
				.flatMap(dummy ->
					webClientBuilder
							.build()
							.post()
							.uri(performPaymentUri)
							.body(Mono.just(bookDto.paymentDto()), PaymentDto.class)
							.retrieve()
							.onStatus(status -> status != HttpStatus.OK, e -> Mono.error(new EntityNotFoundException("teste")))
							.bodyToMono(TransactionDto.class)

				)
				.subscribeOn(Schedulers.boundedElastic());
	}

	public Mono<Void> deleteAll(){
		return this.checkoutRepository.deleteAll();
	}


}
