package com.airjnc.user.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserInquiryPasswordViaEmailReq {

  @Email
  @NotBlank
  private final String email;

  public UserInquiryPasswordViaEmailReq(String email) {
    this.email = email;
  }
}
