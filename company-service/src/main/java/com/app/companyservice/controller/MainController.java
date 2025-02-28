package com.app.companyservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping()
@Slf4j
public class MainController {
  @Value("${HOSTNAME:localhost}")
  private String hostname;

	@GetMapping("/call")
  	public Mono<ResponseEntity<String>> callService() {
		return Mono.just(ResponseEntity.ok().body("Company service"));
  	}

	@GetMapping("/call/instance")
	public Mono<ResponseEntity<String>> callInstance(){
		return Mono.just(ResponseEntity.ok().body(hostname));
	}
}

