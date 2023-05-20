package br.ufrn.imd.reservagowebflux.payment.service;

import br.ufrn.imd.reservagowebflux.payment.model.Transaction;
import br.ufrn.imd.reservagowebflux.payment.model.dto.PaymentDto;
import br.ufrn.imd.reservagowebflux.payment.model.dto.TransactionDto;
import br.ufrn.imd.reservagowebflux.base.service.GenericService;
import br.ufrn.imd.reservagowebflux.payment.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class TransactionService extends GenericService<Transaction, TransactionDto, String> {
	private final TransactionRepository transactionRepository;

	@Autowired
	public TransactionService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}
	@Override
	protected ReactiveMongoRepository<Transaction, String> repository() {
		return this.transactionRepository;
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
