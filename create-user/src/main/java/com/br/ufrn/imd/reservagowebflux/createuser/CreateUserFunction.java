package com.br.ufrn.imd.reservagowebflux.createuser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.function.Function;

@Configuration
public class CreateUserFunction {
    @Bean
    public Function<String, String> createUser() {
        return str -> {
            System.out.println("Chamando a função de criação de usuário");
            return str;
        };
    }
}
