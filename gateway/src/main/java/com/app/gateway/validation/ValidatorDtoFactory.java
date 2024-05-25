package com.app.gateway.validation;

import com.app.gateway.dto.CompanyDto;
import com.app.gateway.dto.DTO;
import com.app.gateway.dto.UserDto;
import com.app.gateway.validation.dto.CompanyDtoValidator;
import com.app.gateway.validation.dto.UserDtoValidator;
import java.util.HashMap;
import java.util.Map;

public class ValidatorDtoFactory {
  private static final Map<Class<?>, Validator> validators = new HashMap<>();

  static {
    validators.put(UserDto.class, new UserDtoValidator());
    validators.put(CompanyDto.class, new CompanyDtoValidator());
  }

  public static <T extends DTO> Validator getValidator(Class<T> clazz){
    return validators.get(clazz);
  }

  public static boolean isValid(Class<?> dtoClass, DTO dto){
    return validators.get(dtoClass).isValid(dto);
  }
}
