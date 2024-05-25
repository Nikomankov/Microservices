package com.app.companyservice.dto;

import com.app.companyservice.validation.annotation.ValidEmail;
import com.app.companyservice.validation.annotation.ValidName;
import com.app.companyservice.validation.annotation.ValidPhoneNumber;
import lombok.Data;

@Data
public class CompanyDto {
  @ValidName(message = "Name validation failed")
  private String name;
  @ValidPhoneNumber(message = "Phone number validation failed")
  private String phoneNumber;
  @ValidEmail(message = "Email validation failed")
  private String email;
}
