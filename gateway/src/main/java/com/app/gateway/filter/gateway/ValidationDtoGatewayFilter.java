package com.app.gateway.filter.gateway;

import com.app.gateway.dto.DTO;
import com.app.gateway.exceptions.ValidationException;
import com.app.gateway.dto.DtoClassFactory;
import com.app.gateway.validation.ValidatorDtoFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This class implements dto validation as a GatewayFilter for the stream of requests passing
 * through routes.
 * .
 * IMPORTANT!
 * Validation requires reading the request body, and thus we violate the integrity of the stream.
 * When data is read from a stream, it "leaves" the stream. For example, when you read a byte
 * from a stream, that byte is moved from the stream's buffer to your application and is no
 * longer available in the original stream.
 * To avoid this situation, after reading, we create a new request through the decorator and
 * pass it further into the stream.
 * This implementation is a crutch
 */
@Component
@Slf4j
public class ValidationDtoGatewayFilter extends AbstractGatewayFilterFactory<ValidationDtoGatewayFilter.Config> {
  public ValidationDtoGatewayFilter() {
    super(Config.class);
  }

  @Data
  @AllArgsConstructor
  public static class Config {
    String service;
  }

  @Override
  public GatewayFilter apply(Config config) {
    log.info("""
            Service: {}
            Method {} invoke
            """,
        "Gateway", this.getClass());

    return (exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();

      //Get class by request (method and path)
      Class<? extends DTO> dtoClass = DtoClassFactory.getDtoClassByRequest(request);

      //If there are no matches, then the request does not require body validation
      if (dtoClass == null) {
        return chain.filter(exchange);
      }

      //Read body and validate it
      return DataBufferUtils.join(exchange.getRequest().getBody())
          .flatMap(dataBuffer -> {
            byte[] bytes = new byte[dataBuffer.readableByteCount()];
            dataBuffer.read(bytes);
            DataBufferUtils.release(dataBuffer);

            ObjectMapper objectMapper = new ObjectMapper();
            try {
              //Deserialize DTO
              DTO dto = objectMapper.readValue(bytes, dtoClass);

              //Validate DTO
              if (ValidatorDtoFactory.isValid(dtoClass, dto)) {
                ServerHttpRequest newRequest = exchange.getRequest().mutate()
                    .uri(request.getURI())
                    .headers(httpHeaders -> httpHeaders.putAll(request.getHeaders()))
                    .build();

                // Create a new request with cached body
                // if we cant do it, then our request is failed
                newRequest = new ServerHttpRequestDecorator(newRequest) {
                  @Override
                  public Flux<DataBuffer> getBody() {
                    return Flux.just(exchange.getResponse().bufferFactory().wrap(bytes));
                  }
                };

                //Continue the filter chain with the new request
                return chain.filter(exchange.mutate().request(newRequest).build());
              } else {
                throw new ValidationException(String.format("Validation %s failed", dtoClass.getName()));
              }

            } catch (Exception e) {
              exchange.getResponse().getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
              exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
              String errorMsg = "{\"error\": \"Invalid input\", \"details\": \"" + e.getMessage() + "\"}";
              return exchange.getResponse()
                  .writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(errorMsg.getBytes())));
            }
          });
    };
  }
}
