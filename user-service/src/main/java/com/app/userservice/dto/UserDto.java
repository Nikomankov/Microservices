package com.app.userservice.dto;

import lombok.Data;

@Data
public class UserDto {
  private int id;
  private String firstName;
  private String lastName;
  private String middleName;
  private String email;
  private String phoneNumber;
}