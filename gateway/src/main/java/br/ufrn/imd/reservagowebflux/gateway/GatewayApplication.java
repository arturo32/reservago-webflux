package br.ufrn.imd.reservagowebflux.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

	public static final String ADMIN_SERVER_URI = "http://localhost:8100";
	public static final String PAYMENT_SERVER_URI = "http://localhost:8101";
	public static final String CHECKOUT_SERVER_URI = "http://localhost:8102";

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}



//	@Bean
//	public RouteLocator routes(RouteLocatorBuilder builder) {
//		return  builder.routes()
//				.route(p -> p
//						.path("/admin")
//						.uri(ADMIN_SERVER_URI)
//				)
//				.route(p -> p
//						.path("/payment")
//						.uri(PAYMENT_SERVER_URI)
//				)
//				.route(p -> p
//						.path("/checkout")
//						.uri(CHECKOUT_SERVER_URI)
//				)
//				.build();
//	}
}
