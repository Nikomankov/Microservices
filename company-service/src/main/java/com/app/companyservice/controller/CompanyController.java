package com.app.companyservice.controller;

import com.app.companyservice.dto.CompanyDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/companies")
@AllArgsConstructor
public class CompanyController {
  private Validator defaultValidator;

  @PostMapping
  public Mono<ResponseEntity<String>> saveCompany(@RequestBody CompanyDto companyDto){
    Set<ConstraintViolation<CompanyDto>> violationSet = defaultValidator.validate(companyDto);
    if(violationSet.isEmpty()){
      return Mono.just(ResponseEntity.ok().body("the request with company reached the service"));
    } else {
      StringBuilder builder = new StringBuilder();
      violationSet.forEach(v -> builder.append(v.getMessage()).append("\n"));
      return Mono.just(ResponseEntity.badRequest().body(builder.toString()));
    }
  }

  @GetMapping
  public ResponseEntity<String> getSomething(){
    return ResponseEntity.ok().body("the request with company reached the service");
  }
}
