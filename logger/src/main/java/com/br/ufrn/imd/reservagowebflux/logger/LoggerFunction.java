package com.br.ufrn.imd.reservagowebflux.logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.function.Consumer;

@Configuration
public class LoggerFunction {

    @Bean
    public Consumer<String> log() {
        return message -> {
            System.out.println("[LOGGER](" + new Date() + "): " + message);
        };
    }
}
