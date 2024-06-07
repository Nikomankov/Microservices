package com.app.companyservice.filter;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * This filter get data from request headers and set them as labels to MDC for next logging
 */
@Component
public class MDCWebFilter implements WebFilter {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		return Mono.deferContextual(contextView -> {
			String userId = exchange.getRequest().getHeaders().getFirst("userId");

			if (userId != null) {
				MDC.put("userId", userId);
			}

			return chain.filter(exchange).doFinally(signalType -> MDC.clear());
		});
	}
}
