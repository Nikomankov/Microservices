package com.app.gateway.config;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "services")
public class ServiceProperties {
  private Map<String, String> uri;

  public String getUriByName(String serviceName){
    return uri.get(serviceName);
  }

}
