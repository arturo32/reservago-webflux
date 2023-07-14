package com.br.ufrn.imd.reservagowebflux.processplacecreation;

import com.br.ufrn.imd.reservagowebflux.processplacecreation.models.Place;
import com.br.ufrn.imd.reservagowebflux.processplacecreation.repositories.PlaceRepository;
import com.br.ufrn.imd.reservagowebflux.processplacecreation.services.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;
import java.util.function.Function;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

@Configuration
public class ProcessPlaceCreationFunction {

    private final PlaceService placeService;

    @Autowired
    public ProcessPlaceCreationFunction(PlaceService placeService) {
        this.placeService = placeService;
    }

    @Bean
    public Function<String, String> processPlaceCreation() {
        return str -> {
            String[] placeParams = str.split(":");

            if (placeParams.length != 6) return "Payload Error. Wrong number of params.";

            try {
                Place place = new Place();
                place.setName(placeParams[0]);
                place.setAvailable(true);
                place.setDescription(placeParams[1]);
                place.setLocation(placeParams[2]);
                place.setStars(parseFloat(placeParams[3]));
                place.setValuePerDay(parseFloat(placeParams[4]));
                place.setMaxNumberOfGuests(parseInt(placeParams[5]));
                place.setId(UUID.randomUUID().toString());

                this.placeService.save(this.placeService.convertToDto(place));

                return "Place " + place.getName() + " created with ID " + place.getId();
            } catch (Exception e) {
                return "Error on place creation";
            }
        };
    }
}
