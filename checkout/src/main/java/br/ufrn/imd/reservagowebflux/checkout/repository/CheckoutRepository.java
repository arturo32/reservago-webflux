package br.ufrn.imd.reservagowebflux.checkout.repository;

import br.ufrn.imd.reservagowebflux.checkout.model.Checkout;
import java.time.LocalDateTime;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CheckoutRepository extends ReactiveMongoRepository<Checkout, String> {
	Mono<Integer> countAllByPlaceIdAndActiveIsTrueAndCheckoutDateGreaterThan(Long placeId, LocalDateTime currentDate);
}
