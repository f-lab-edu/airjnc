package com.airjnc.mail.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SendUsingTemplateDto {

  private final String name;

  private final String code;

  @Builder
  public SendUsingTemplateDto(String name, String code) {
    this.name = name;
    this.code = code;
  }
}
