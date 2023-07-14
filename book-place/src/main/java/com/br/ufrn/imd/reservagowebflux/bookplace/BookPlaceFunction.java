package com.br.ufrn.imd.reservagowebflux.bookplace;

import org.springframework.context.annotation.Bean;

import java.util.function.Function;

public class BookPlaceFunction {

    @Bean
    public Function<String, String> bookPlace() {
        return str -> {


            return "Place as booked with ID";
        };
    }t
}
