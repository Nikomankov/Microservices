package com.app.gateway.dto;

import lombok.Data;

@Data
public class CompanyDto implements DTO{
  private String name;
  private String phoneNumber;
  private String email;
}
