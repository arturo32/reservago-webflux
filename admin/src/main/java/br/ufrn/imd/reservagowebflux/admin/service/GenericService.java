package br.ufrn.imd.reservagowebflux.admin.service;

import br.ufrn.imd.reservagowebflux.admin.model.GenericModel;
import br.ufrn.imd.reservagowebflux.admin.model.Place;
import java.io.Serializable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public abstract class GenericService<T extends GenericModel<PK>, PK extends Serializable> {

	protected abstract ReactiveMongoRepository<T, PK> repository();

	public Mono<T> save(T place) {
		return this.repository().save(place);
	}

	public Mono<T> findById(PK placeId) {
		return this.repository().findById(placeId);
	}
}
