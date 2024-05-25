package com.app.gateway.dto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * This class compares information from the request (path and method) and, based on this,
 * returns the dto class passed in this request. This is necessary to provide validation
 * in the UserServiceValidationDtoGatewayFilter class
 */
@Slf4j
public class DtoClassFactory {
  public static Class<? extends DTO> getDtoClassByRequest(ServerHttpRequest request){
    log.info("""
            Service: {}
            Method {} invoke
            """,
        "Gateway", DtoClassFactory.class.getName());

    String path = request.getPath().toString();
    String method = request.getMethod().toString();

    if(path.equals("POST") && method.equals("/users")){
      return UserDto.class;
    } else return null;
  }
}
