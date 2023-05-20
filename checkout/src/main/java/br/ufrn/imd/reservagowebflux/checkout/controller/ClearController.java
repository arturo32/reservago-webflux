package br.ufrn.imd.reservagowebflux.checkout.controller;

import br.ufrn.imd.reservagowebflux.checkout.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("clear")
public class ClearController {
	private final CheckoutService checkoutService;

	@Autowired
	public ClearController(CheckoutService checkoutService) {
		this.checkoutService = checkoutService;
	}

	@DeleteMapping
	public Mono<Void> clear() {
		return this.checkoutService.deleteAll();
	}
}
