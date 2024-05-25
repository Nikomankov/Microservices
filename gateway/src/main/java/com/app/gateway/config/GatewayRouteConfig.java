package com.app.gateway.config;

import com.app.gateway.filter.gateway.ValidationDtoGatewayFilter;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class GatewayRouteConfig {
  private ServiceProperties serviceProperties;

  @Bean
  public RouteLocator gatewayRouteLocator(RouteLocatorBuilder builder,
                                          ValidationDtoGatewayFilter validationDtoGatewayFilter){
    return builder.routes()

        .route("user-service", r -> r
            .path("/user-service/**")
            .filters(f -> f.stripPrefix(1)
                .filter(validationDtoGatewayFilter.apply(new ValidationDtoGatewayFilter.Config("user-service")))
            )
            .uri(serviceProperties.getUriByName("user-service")))

        .route("company-service", r -> r
            .path("/company-service/**")
            .filters(f -> f.stripPrefix(1)

            )
            .uri(serviceProperties.getUriByName("company-service")))
        .build();
  }
}
