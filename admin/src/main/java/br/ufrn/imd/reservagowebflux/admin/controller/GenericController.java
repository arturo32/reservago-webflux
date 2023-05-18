package br.ufrn.imd.reservagowebflux.admin.controller;

import br.ufrn.imd.reservagowebflux.admin.exception.EntityNotFoundException;
import br.ufrn.imd.reservagowebflux.admin.model.GenericModel;
import br.ufrn.imd.reservagowebflux.admin.service.GenericService;
import jakarta.validation.Valid;
import java.io.Serializable;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class GenericController<T extends GenericModel<PK>, Dto, PK extends Serializable>{

	protected abstract GenericService<T, Dto, PK> service();

	@GetMapping
	public Flux<Dto> findAll() {
		return this.service().findAll()
				.map(service()::convertToDto);
	}

	@GetMapping({"/{id}"})
	public Mono<Dto> findById(@PathVariable PK id) {
		return this.service().findById(id)
				.map(service()::convertToDto)
				.onErrorMap(EntityNotFoundException.class,
						e -> new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e));
	}

	@PostMapping
	public Mono<Dto> save(@RequestBody @Valid Dto dto) {
		return this.service().save(dto)
				.map(service()::convertToDto)
				.onErrorMap(EntityNotFoundException.class,
						e -> new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e));
	}
}
