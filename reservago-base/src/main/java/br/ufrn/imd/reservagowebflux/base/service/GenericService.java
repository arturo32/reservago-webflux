package br.ufrn.imd.reservagowebflux.base.service;

import br.ufrn.imd.reservagowebflux.base.exception.EntityNotFoundException;
import br.ufrn.imd.reservagowebflux.base.model.GenericModel;
import java.io.Serializable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public abstract class GenericService<T extends GenericModel<PK>, Dto,  PK extends Serializable> {

	protected abstract ReactiveMongoRepository<T, PK> repository();

	public Mono<T> save(Dto dto) {
		return this.convertToEntity(Mono.just(dto))
				.flatMap(e -> this.repository().save(e))
				.subscribeOn(Schedulers.boundedElastic());
	}

	public Mono<T> findById(PK id) {
		return this.repository().findById(id)
				.switchIfEmpty(Mono.error(new EntityNotFoundException("Entity of id " + id + " not found.")))
				.subscribeOn(Schedulers.boundedElastic());
	}

	public Flux<T> findAll() {
		return this.repository().findAll()
				.subscribeOn(Schedulers.boundedElastic());
	}

	public Mono<Void> deleteAll() {
		return this.repository().deleteAll()
				.subscribeOn(Schedulers.boundedElastic());
	}

	public abstract Dto convertToDto(T entity);

	public abstract Mono<T> convertToEntity(Mono<Dto> dto);

}
