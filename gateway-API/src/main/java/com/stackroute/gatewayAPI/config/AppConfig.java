package com.stackroute.gatewayAPI.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class AppConfig {
    @Bean
    RouteLocator getRoutes(RouteLocatorBuilder builder){
        return builder.routes()
//                .route(p -> p.path("/authentication/**").uri("http://authenticationService:9999/*"))
//                .route(p -> p.path("/productAuth/**").uri("http://productService:8888/*"))
//                .route(p -> p.path("/authentication/**").uri("http://localhost:9999/*"))
//                .route(p -> p.path("/productAuth/**").uri("http://localhost:8888/*"))
                .route(p -> p.path("/kanbandetails/**").uri("http://localhost:8888/"))
                .route(p -> p.path("/login/**").uri("http://localhost:9999/*"))
                .build();
    }
}
