package br.ufrn.imd.reservagowebflux.checkout.controller;

import br.ufrn.imd.reservagowebflux.checkout.model.dto.BookDto;
import br.ufrn.imd.reservagowebflux.checkout.model.dto.CheckoutDto;
import br.ufrn.imd.reservagowebflux.checkout.model.dto.TransactionDto;
import br.ufrn.imd.reservagowebflux.checkout.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("checkout")
public class CheckoutController {

	private final CheckoutService checkoutService;

	@Autowired
	public CheckoutController(CheckoutService checkoutService) {
		this.checkoutService = checkoutService;
	}

	@GetMapping({"/verify/{id}"})
	public Mono<CheckoutDto> checkAvailability(@PathVariable Long id) {
		return checkoutService.checkAvailability(id);
	}

	@PostMapping({"/book/{placeId}"})
	public Mono<TransactionDto> bookLocation(@PathVariable Long placeId,
			@RequestBody BookDto bookDto) {
		return checkoutService.bookLocation(placeId, bookDto);
	}


}
