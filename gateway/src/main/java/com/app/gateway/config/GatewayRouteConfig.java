package com.app.gateway.config;

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
  public RouteLocator gatewayRouteLocator(RouteLocatorBuilder builder){
    return builder.routes()
        .route("service-1", r -> r
            .path("/service1/**")
            .filters(f -> f.stripPrefix(1)
//                                    .filter()             //filter to validate by path and method
            )
            .uri(serviceProperties.getUriByName("service1")))
        .route("service-2", r -> r
            .path("/service2/**")
            .filters(f -> f.stripPrefix(1)
//                                    .filter()             //filter to validate by path and method
            )
            .uri(serviceProperties.getUriByName("service2")))
        .build();
  }
}
