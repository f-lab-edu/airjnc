package com.airjnc.user.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserResetPwdCodeViaEmailDTO {

  @Email
  @NotBlank
  private final String email;

  public UserResetPwdCodeViaEmailDTO(String email) {
    this.email = email;
  }
}
