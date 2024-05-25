package com.app.gateway.validation.dto;

import com.app.gateway.dto.UserDto;
import com.app.gateway.validation.Validator;
import com.app.gateway.validation.field.EmailValidator;
import com.app.gateway.validation.field.NameValidator;
import com.app.gateway.validation.field.PhoneNumberValidator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserDtoValidator implements Validator<UserDto> {

  @Override
  public boolean isValid(UserDto user) {
    log.info("""
            Service: {}
            Method {} invoke
            """,
        "Gateway", this.getClass());

    return NameValidator.isValid(user.getFirstName()) &&
          NameValidator.isValid(user.getLastName()) &&
          NameValidator.isValid(user.getMiddleName()) &&
          PhoneNumberValidator.isValid(user.getPhoneNumber()) &&
          EmailValidator.isValid(user.getEmail());
  }
}
