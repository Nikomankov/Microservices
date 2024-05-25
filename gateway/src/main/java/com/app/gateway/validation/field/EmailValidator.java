package com.app.gateway.validation.field;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailValidator {
  private static final String EMAIL_PATTERN = "^[A-Za-z\\d._%+-]+@[A-Za-z\\d.-]+\\.[A-Za-z]{2,}$";

  public static boolean isValid(String email){
    log.info("""
            Service: {}
            Method {} invoke
            """,
        "Gateway", EmailValidator.class.getName());

    return email != null && email.matches(EMAIL_PATTERN);
  }
}
