package com.app.gateway.config;

import io.micrometer.tracing.exporter.SpanExportingPredicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TracingConfig {

	/**
	 * Exclude actuator endpoints from tracing
	 * @return - span without actuator
	 */
	@Bean
	SpanExportingPredicate noActuator() {
		return span -> span.getTags().get("uri") == null || !span.getTags().get("uri").startsWith("/actuator");
	}
}
