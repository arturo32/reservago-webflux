package br.ufrn.imd.reservagowebflux.admin.service;

import br.ufrn.imd.reservagowebflux.admin.model.GenericModel;
import java.io.Serializable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class GenericService<T extends GenericModel<PK>, Dto,  PK extends Serializable> {

	protected abstract ReactiveMongoRepository<T, PK> repository();

	public Mono<T> save(Dto dto) {
		return this.convertToEntity(Mono.just(dto))
				.flatMap(e -> this.repository().save(e));
	}

	public Mono<T> findById(PK placeId) {
		return this.repository().findById(placeId);
	}

	public Flux<T> findAll() {
		return this.repository().findAll();
	}

	public abstract Dto convertToDto(T entity);

	public abstract Mono<T> convertToEntity(Mono<Dto> dto);

}
