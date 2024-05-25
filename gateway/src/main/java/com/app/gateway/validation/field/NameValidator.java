package com.app.gateway.validation.field;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NameValidator {
  private static final String NAME_PATTERN = "^[A-Za-zА-Яа-я\\s\\-]+$";

  public static boolean isValid(String name){
    log.info("""
            Service: {}
            Method {} invoke
            """,
        "Gateway", NameValidator.class.getName());

    return name != null && name.matches(NAME_PATTERN);
  }
}
