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

	@Value("${admin.server.name}")
	private String ADMIN_SERVER_URL;

	@Value("${payment.server.name}")
	private String PAYMENT_SERVER_URL;

	@Autowired
	public CheckoutService(CheckoutRepository checkoutRepository, Builder webClientBuilder) {
		this.checkoutRepository = checkoutRepository;
		this.webClientBuilder = webClientBuilder;
	}

	public Mono<CheckoutDto> checkAvailability(String placeId) {
		String adminUri = "http://" + ADMIN_SERVER_URL + "/place/" + placeId;
		return webClientBuilder
				.build()
				.get()
				.uri(adminUri)
				.retrieve()
				.onStatus(status -> status != HttpStatus.OK, e -> Mono.error(new EntityNotFoundException("teste")))
				.bodyToMono(PlaceDto.class)
				.map(PlaceDto::maxNumberOfGuests)
				.flatMap(maxNumberOfGuests ->
					this.checkoutRepository.countAllByPlaceIdAndActiveIsTrueAndCheckoutDateGreaterThan(placeId, LocalDateTime.now())
							.map(currentGuests -> new CheckoutDto(maxNumberOfGuests,
									Math.toIntExact(currentGuests)))
							.switchIfEmpty(Mono.error(new EntityNotFoundException("aaaaaaaa")))

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
					return this.checkoutRepository.save(checkout);
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
