package com.app.gateway.dto;

import lombok.Data;

@Data
public class UserDto implements DTO {
  private int id;
  private String firstName;
  private String lastName;
  private String middleName;
  private String email;
  private String phoneNumber;
}