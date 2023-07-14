package com.br.ufrn.imd.reservagowebflux.processusercreation.repositories;


import com.br.ufrn.imd.reservagowebflux.processusercreation.models.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

}
