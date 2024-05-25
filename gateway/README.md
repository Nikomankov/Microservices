# Gateway service

### This service distributes requests between services.

## Some about realization and future plans

### Validation

Some sources say that the gateway must validate incoming requests. I tried to 
implement this as GatewayFilter (ValidationDtoGatewayFilter.class). However, 
this realization looks like a crutch.

