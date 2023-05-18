package br.ufrn.imd.reservagowebflux.payment.controller;

import br.ufrn.imd.reservagowebflux.payment.model.Transaction;
import br.ufrn.imd.reservagowebflux.payment.model.dto.PaymentDto;
import br.ufrn.imd.reservagowebflux.payment.model.dto.TransactionDto;
import br.ufrn.imd.reservagowebflux.payment.service.TransactionService;
import br.ufrn.imd.reservagowebflux.base.controller.GenericController;
import br.ufrn.imd.reservagowebflux.base.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("transaction")
public class TransactionController extends GenericController<Transaction, TransactionDto, String> {

	private final TransactionService transactionService;

	@Autowired
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping("/pay")
	public Mono<Transaction> performPayment(@RequestBody PaymentDto paymentDto) {
		return transactionService.performPayment(paymentDto);
	}

	@Override
	protected GenericService<Transaction, TransactionDto, String> service() {
		return this.transactionService;
	}

}
