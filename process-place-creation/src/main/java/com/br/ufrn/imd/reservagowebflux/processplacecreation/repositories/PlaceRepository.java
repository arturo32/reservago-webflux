package com.br.ufrn.imd.reservagowebflux.processplacecreation.repositories;

import com.br.ufrn.imd.reservagowebflux.processplacecreation.models.Place;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PlaceRepository extends ReactiveMongoRepository<Place, String> {

}
