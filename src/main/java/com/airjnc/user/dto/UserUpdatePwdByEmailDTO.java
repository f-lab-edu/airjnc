package com.airjnc.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserUpdatePwdByEmailDTO {

  private final String email;

  private final String password;

  @Builder
  public UserUpdatePwdByEmailDTO(String email, String password) {
    this.email = email;
    this.password = password;
  }
}
