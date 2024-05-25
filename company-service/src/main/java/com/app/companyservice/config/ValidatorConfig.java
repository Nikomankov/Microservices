package com.app.companyservice.config;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorConfig {

  @Bean
  public Validator defaultValidator(){
    return Validation.buildDefaultValidatorFactory().getValidator();
  }
}
