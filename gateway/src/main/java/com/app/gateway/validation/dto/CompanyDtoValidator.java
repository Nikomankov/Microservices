package com.app.gateway.validation.dto;

import com.app.gateway.dto.CompanyDto;
import com.app.gateway.validation.Validator;
import com.app.gateway.validation.field.EmailValidator;
import com.app.gateway.validation.field.NameValidator;
import com.app.gateway.validation.field.PhoneNumberValidator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompanyDtoValidator implements Validator<CompanyDto> {

  @Override
  public boolean isValid(CompanyDto company) {
    log.info("""
            Service: {}
            Method {} invoke
            """,
        "Gateway", this.getClass());

    return NameValidator.isValid(company.getName()) &&
          PhoneNumberValidator.isValid(company.getPhoneNumber()) &&
          EmailValidator.isValid(company.getEmail());
  }
}
