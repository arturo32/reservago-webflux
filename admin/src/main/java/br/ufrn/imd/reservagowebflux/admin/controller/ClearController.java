package br.ufrn.imd.reservagowebflux.admin.controller;

import br.ufrn.imd.reservagowebflux.admin.service.PlaceService;
import br.ufrn.imd.reservagowebflux.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("clear")
public class ClearController {
	private final PlaceService placeService;
	private final UserService userService;

	@Autowired
	public ClearController(PlaceService placeService, UserService userService) {
		this.placeService = placeService;
		this.userService = userService;
	}

	@DeleteMapping
	public Mono<Void> clear() {
		return this.placeService.deleteAll()
				.then(this.userService.deleteAll());
	}
}
