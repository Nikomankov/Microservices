package com.app.companyservice.validation.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

  private static final String EMAIL_PATTERN = "^[A-Za-z\\d._%+-]+@[A-Za-z\\d.-]+\\.[A-Za-z]{2,}$";

  @Override
  public void initialize(ValidEmail email) {
  }

  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    return email != null && email.matches(EMAIL_PATTERN);
  }
}
