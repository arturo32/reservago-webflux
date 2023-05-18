package br.ufrn.imd.reservagowebflux.admin.repository;

import br.ufrn.imd.reservagowebflux.admin.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

}
