package br.ufrn.imd.reservagowebflux.admin.repository;

import br.ufrn.imd.reservagowebflux.admin.model.Place;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PlaceRepository extends ReactiveMongoRepository<Place, String> {

}
