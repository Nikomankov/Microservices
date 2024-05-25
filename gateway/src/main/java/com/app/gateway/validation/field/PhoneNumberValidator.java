package com.app.gateway.validation.field;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PhoneNumberValidator {
  /**
   * Pattern for Belarus phones like:
   * +375 XX YYY-YY-YY
   *  375 XX YYY-YY-YY
   * +375 740 YYY-YY-YY
   *  375 740 YYY-YY-YY
   *  8 0XX YYY-YY-YY
   *  8 740 YYY-YY-YY
   */
  final static String BELARUS_PATTERN = "^((\\+)?375(740|\\d{2})|8(740|0\\d{2}))\\d{7}$";
  /**
   * Pattern for Russian phones like:
   * +7 XXX YYY-YY-YY
   * 7 XXX YYY-YY-YY
   * 8 XXX YYY-YY-YY
   */
  final static String RUSSIA_PATTERN = "((\\+)?7|8)\\d{10}$";

  public static boolean isValid(String phoneNumber){
    log.info("""
            Service: {}
            Method {} invoke
            """,
        "Gateway", PhoneNumberValidator.class.getName());

    return phoneNumber != null &&
        (phoneNumber.matches(RUSSIA_PATTERN) || phoneNumber.matches(BELARUS_PATTERN));
  }
}
