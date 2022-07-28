package com.airjnc.ncp.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MailSendDTO {

  private final String email;

  private final String name;

  private final String code;


  @Builder
  public MailSendDTO(String email, String name, String code) {
    this.email = email;
    this.name = name;
    this.code = code;
  }
}
