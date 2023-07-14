package com.br.ufrn.imd.reservagowebflux.createplace;

import org.springframework.context.annotation.Bean;

import java.util.function.Function;

public class CreatePlaceFunction {

    @Bean
    public Function<String, String> createPlace() {
        return str -> str;
    }
}
