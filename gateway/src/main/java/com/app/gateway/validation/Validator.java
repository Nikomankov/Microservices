package com.app.gateway.validation;

public interface Validator<T> {
  boolean isValid(T dto);
}
