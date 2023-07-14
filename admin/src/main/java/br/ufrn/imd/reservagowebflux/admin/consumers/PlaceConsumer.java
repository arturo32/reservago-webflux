package br.ufrn.imd.reservagowebflux.admin.consumers;

import org.apache.hc.core5.http.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.BeanProperty;
import java.util.function.Consumer;

@Configuration
public class PlaceConsumer {

    @Bean
    Consumer<String> receiveCreateUser() {
        return msg -> {
            System.out.println("Consumidor recebeu " + msg);
        };
    }

}
