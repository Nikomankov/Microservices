package com.app.userservice;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

@SpringBootApplication
public class UserServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserServiceApplication.class, args);
  }

  /**
   * Ensures that tracing context is automatically propagated across threads and asynchronous
   * boundaries within reactive flows.
   * This is crucial for maintaining a consistent trace throughout the application, even when
   * execution jumps between threads or involves non-blocking operations.
   */
  @PostConstruct
  public void init() {
    Hooks.enableAutomaticContextPropagation();
  }
}
