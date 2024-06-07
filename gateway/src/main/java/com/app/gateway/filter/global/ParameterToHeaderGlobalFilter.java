package com.app.gateway.filter.global;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class ParameterToHeaderGlobalFilter implements GlobalFilter, Ordered {
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		//Set userId like header
		String userId = exchange.getRequest().getQueryParams().getFirst("userId");
		if (userId != null){
			request.mutate().header("userId", userId);
		}
		exchange.mutate().request(request);
		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return 0;
	}
}
