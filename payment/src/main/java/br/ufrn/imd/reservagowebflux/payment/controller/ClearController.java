package br.ufrn.imd.reservagowebflux.payment.controller;

import br.ufrn.imd.reservagowebflux.payment.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("clear")
public class ClearController {
	private final TransactionService transactionService;

	@Autowired
	public ClearController(TransactionService transactionService){
		this.transactionService = transactionService;
	}

	@DeleteMapping
	public Mono<Void> clear() {
		return this.transactionService.deleteAll();
	}
}
