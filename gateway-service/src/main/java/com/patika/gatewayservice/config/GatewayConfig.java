package com.patika.gatewayservice.config;

import com.patika.gatewayservice.enums.RoleType;
import com.patika.gatewayservice.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableHystrix
public class GatewayConfig {

    @Autowired
    AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r.path("/api/v1/auth/**")
                        .filters(f -> f
                                .addRequestHeader("RoleTypes", RoleType.USER.name())
                                .filter(filter)
                        )
                        .uri("lb://auth-service"))

                .route("user-service", r -> r.path("/api/v1/users/**")
                        .filters(f -> f
                                .addRequestHeader("RoleTypes", RoleType.USER.name())
                                .filter(filter)
                        )
                        .uri("lb://user-service"))

                .route("user-service", r -> r.path("/api/v1/users/{email}/roles")
                        .filters(f -> f
                                .addRequestHeader("RoleTypes", RoleType.ADMIN.name())
                                .filter(filter)
                        )
                        .uri("lb://user-service"))

                .route("ticket-service", r -> r.path("/api/v1/voyages/getVoyages", "/api/v1/bookings/**", "/api/v1/tickets/**")
                        .filters(f -> f
                                .addRequestHeader("RoleTypes", RoleType.USER.name())
                                .filter(filter)
                        )
                        .uri("lb://ticket-service"))

                .route("ticket-service", r -> r.path("/api/v1/voyages/**")
                        .filters(f -> f
                                .addRequestHeader("RoleTypes", RoleType.ADMIN.name())
                                .filter(filter)
                        )
                        .uri("lb://ticket-service"))

                .build();
    }

}
