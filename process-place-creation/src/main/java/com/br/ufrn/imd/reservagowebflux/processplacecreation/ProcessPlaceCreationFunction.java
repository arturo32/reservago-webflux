package com.br.ufrn.imd.reservagowebflux.processplacecreation;

import com.br.ufrn.imd.reservagowebflux.processplacecreation.models.Place;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

public class ProcessPlaceCreationFunction {

    @Bean
    public Function<String, String> processPlaceCreation() {
        return str -> {
            String[] placeParams = str.split(":");

            if (placeParams.length != 10) return "Payload Error. Wrong number of params.";

            try {
                Place place = new Place(
                        placeParams[0],
                        placeParams[1],
                        placeParams[2],
                        placeParams[3],
                        placeParams[4],
                        placeParams[5],
                        placeParams[6],
                        placeParams[7]
                );
            } catch (Exception e) {
                return "Error on place creation";
            }

            return "Place " + placeParams[0] + " created.";
        };
    }
}
