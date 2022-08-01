package com.airjnc.user.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserinquiryPasswordViaEmailReq {

  @Email
  @NotBlank
  private final String email;

  public UserinquiryPasswordViaEmailReq(String email) {
    this.email = email;
  }
}
