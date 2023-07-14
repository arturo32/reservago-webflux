package com.br.ufrn.imd.reservagowebflux.verifyplaceavailable.repositories;


import com.br.ufrn.imd.reservagowebflux.verifyplaceavailable.models.Place;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PlaceRepository extends ReactiveMongoRepository<Place, String> {

}
