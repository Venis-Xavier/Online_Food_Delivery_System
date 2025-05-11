package com.cts.apigateway.route;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Route {

    @Bean
    RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("payment-service", r -> r.path("/api/payment/**")
                        .uri("http://localhost:8081"))
                .route("menu-service", r -> r.path("/api/menus/**")
                        .uri("http://localhost:8082"))
                .route("menu-service", r -> r.path("/api/restaurant/**")
                        .uri("http://localhost:8082"))
                .route("delivery-service", r -> r.path("/api/delivery/**")
                        .uri("http://localhost:8083"))
                .route("order-service", r -> r.path("/api/order/**")
                        .uri("http://localhost:8084"))
                .route("user-service", r -> r.path("/api/auth/**")
                        .uri("http://localhost:8085"))
                .build();
    }
    
}

