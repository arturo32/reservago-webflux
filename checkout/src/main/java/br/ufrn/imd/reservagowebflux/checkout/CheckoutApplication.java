package br.ufrn.imd.reservagowebflux.checkout;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@EnableCaching
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Checkout API REST", version = "1.0", description = "API documentation of Checkout service"))
public class CheckoutApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckoutApplication.class, args);
	}

//	@Bean
//	@LoadBalanced
//	public WebClient.Builder loadBalancedWebClientBuilder() {
//		return WebClient.builder();
//	}

}
