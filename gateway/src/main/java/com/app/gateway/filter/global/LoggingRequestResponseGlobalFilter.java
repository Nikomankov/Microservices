package com.app.gateway.filter.global;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoggingRequestResponseGlobalFilter implements GlobalFilter, Ordered {
  private final Logger logger = LoggerFactory.getLogger(LoggingRequestResponseGlobalFilter.class);

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    ServerHttpResponse response = exchange.getResponse();

    String userId = exchange.getRequest().getHeaders().getFirst("userId");

    if (userId != null) {
      MDC.put("userId", userId);
    }

    return chain.filter(exchange).then(Mono.fromRunnable(() -> logger.info(
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
