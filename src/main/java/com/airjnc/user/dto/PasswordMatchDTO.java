package com.airjnc.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PasswordMatchDTO {

  private final String password;

  private final String hash;

  @Builder
  public PasswordMatchDTO(String password, String hash) {
    this.password = password;
    this.hash = hash;
  }
}
