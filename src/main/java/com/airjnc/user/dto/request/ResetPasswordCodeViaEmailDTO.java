package com.airjnc.user.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ResetPasswordCodeViaEmailDTO {

  @Email
  @NotNull
  private final String email;

  public ResetPasswordCodeViaEmailDTO(String email) {
    this.email = email;
  }
}
