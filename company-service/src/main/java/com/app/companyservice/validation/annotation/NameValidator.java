package com.app.companyservice.validation.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<ValidName, String> {

  private static final String NAME_PATTERN = "^[A-Za-zА-Яа-я\\s\\-]+$";

  @Override
  public void initialize(ValidName name) {
  }

  @Override
  public boolean isValid(String name, ConstraintValidatorContext context) {
    return name != null && name.matches(NAME_PATTERN);
  }
}
