package com.app.gateway.filter.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingRequestResponseGlobalFilter implements GlobalFilter, Ordered {

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    ServerHttpResponse response = exchange.getResponse();
    return chain.filter(exchange).then(Mono.fromRunnable(() -> log.info(
        """
        
        ------------------------------
        --REQUEST--
        id: {}
        method: {}
        uri: {}
        
        --RESPONSE--
        status code: {}
        ------------------------------""",
        request.getId(), request.getMethod(), request.getURI(), response.getStatusCode())));
  }

  @Override
  public int getOrder() {
    return -1;
  }
}
