package com.app.gateway.exceptions;

public class ServiceNotFoundException extends Exception{

  public ServiceNotFoundException(String serviceName){
    super(serviceName);
  }

}
