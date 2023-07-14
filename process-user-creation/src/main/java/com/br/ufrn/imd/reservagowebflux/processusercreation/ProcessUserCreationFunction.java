package com.br.ufrn.imd.reservagowebflux.processusercreation;

import com.br.ufrn.imd.reservagowebflux.processusercreation.models.User;
import com.br.ufrn.imd.reservagowebflux.processusercreation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Function;

import static java.lang.Integer.parseInt;

@Configuration
public class ProcessUserCreationFunction {

    @Autowired
    private final UserService userService;

    public ProcessUserCreationFunction(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public Function<String, String> processUserCreation() {
        return str -> {
            System.out.println("Processando a criação de usuário");
            String[] userParams = str.split(":");

            if (userParams.length != 2) return "Payload error. Must have exactly 2 arguments.";
            if (!userParams[1].matches("[0-9]")) return "Payload error. Type must be a number.";

            User user = new User(userParams[0], parseInt(userParams[1]));

            try {
                this.userService.save(this.userService.convertToDto(user));
            } catch (Exception e) {
                return "Error in user creation";
            }

            return "User " + userParams[0] + " created.";
        };
    }
}
