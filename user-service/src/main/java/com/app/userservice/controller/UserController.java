package com.app.userservice.controller;

import com.app.userservice.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

  @PostMapping
  public Mono<ResponseEntity<String>> saveUser(@RequestBody UserDto userDto){
    return Mono.just(ResponseEntity.ok().body("the request with user reached the service"));
  }
  @GetMapping
  public Mono<ResponseEntity<String>> getUser(){
    return Mono.just(ResponseEntity.ok().body("Some user"));
  }
}
