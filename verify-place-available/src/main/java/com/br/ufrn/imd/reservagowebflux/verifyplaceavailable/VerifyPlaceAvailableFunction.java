package com.br.ufrn.imd.reservagowebflux.verifyplaceavailable;

import com.br.ufrn.imd.reservagowebflux.verifyplaceavailable.services.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
public class VerifyPlaceAvailableFunction {

    private final PlaceService placeService;

    @Autowired
    public VerifyPlaceAvailableFunction(PlaceService placeService) {
        this.placeService = placeService;
    }

    @Bean
    public Function<String, Mono<String>> verifyPlaceAvailable() {
        return placeId -> this.placeService
                .checkPlaceAvailability(placeId)
                .map(result -> result ? "Place " + placeId + " is available" : "Place is not available");
    }
}
