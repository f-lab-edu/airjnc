package com.airjnc.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdatePasswordByEmailDTO {

  private final String email;

  private final String password;

  @Builder
  public UpdatePasswordByEmailDTO(String email, String password) {
    this.email = email;
    this.password = password;
  }
}
