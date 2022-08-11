package com.airjnc.mail.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SendUsingTemplateDto {

  private final String name;

  private final String certificationCode;

  @Builder
  public SendUsingTemplateDto(String name, String certificationCode) {
    this.name = name;
    this.certificationCode = certificationCode;
  }
}
