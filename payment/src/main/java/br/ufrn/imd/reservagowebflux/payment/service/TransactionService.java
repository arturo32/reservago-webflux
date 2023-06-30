package br.ufrn.imd.reservagowebflux.payment.service;

import br.ufrn.imd.reservagowebflux.base.exception.EntityNotFoundException;
import br.ufrn.imd.reservagowebflux.payment.model.Transaction;
import br.ufrn.imd.reservagowebflux.payment.model.dto.PaymentDto;
import br.ufrn.imd.reservagowebflux.payment.model.dto.TransactionDto;
import br.ufrn.imd.reservagowebflux.base.service.GenericService;
import br.ufrn.imd.reservagowebflux.payment.repository.TransactionRepository;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.LocalCachedMapOptions.ReconnectionStrategy;
import org.redisson.api.LocalCachedMapOptions.SyncStrategy;
import org.redisson.api.RLocalCachedMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class TransactionService extends GenericService<Transaction, TransactionDto, String> {
	private final TransactionRepository transactionRepository;

	private RLocalCachedMapReactive<String, Transaction> transactionMap;

	@Autowired
	public TransactionService(TransactionRepository transactionRepository, RedissonReactiveClient cacheser) {
		this.transactionRepository = transactionRepository;
		LocalCachedMapOptions<String, Transaction> mapOptions = LocalCachedMapOptions.<String, Transaction>defaults()
				.syncStrategy(SyncStrategy.UPDATE) // If data changes, redis update the local cache of others services
				.reconnectionStrategy(ReconnectionStrategy.CLEAR); // If connection fails, local cache is cleaned after reconnection
		this.transactionMap = cacheser.getLocalCachedMap("/transaction/", new TypedJsonJacksonCodec(String.class, Transaction.class), mapOptions);
	}
	@Override
	protected ReactiveMongoRepository<Transaction, String> repository() {
		return this.transactionRepository;
	}

	@Override
	public Mono<Transaction> findById(String id) {
		return this.transactionMap.get(id)
				.switchIfEmpty(
						this.repository().findById(id)
								.switchIfEmpty(Mono.error(new EntityNotFoundException("Entity of id " + String.valueOf(id) + " not found.")))
								.subscribeOn(Schedulers.boundedElastic())
								.flatMap(c -> this.transactionMap.fastPut(id, c).thenReturn(c))
				);
	}

	@Override
	public TransactionDto convertToDto(Transaction place) {
		return new TransactionDto(place);
	}

	@Override
	public Mono<Transaction> convertToEntity(Mono<TransactionDto> transactionDto) {
		return transactionDto
				.map(dto -> {
					Transaction transaction = new Transaction();
					transaction.setId(dto.id());
					transaction.setTransactionOk(dto.transactionOk());
					transaction.setPlaceId(dto.placeId());
					transaction.setUserId(dto.userId());
					return transaction;
				})
				.subscribeOn(Schedulers.boundedElastic());
	}

	public Mono<Transaction> performPayment(PaymentDto paymentDto) {

		// Do some validation logic
		Transaction transaction = new Transaction();
		transaction.setUserId(paymentDto.creditCard().ownerId());
		transaction.setTransactionOk(true);
		//transaction.setPlaceId();....

		// Payment will be used, I swear
		return this.repository().save(transaction);
	}



}
