package com.airjnc.mail.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MailSendCertificationCodeToEmailReq {

  @NotBlank
  @Email
  private String email;

  @Builder
  public MailSendCertificationCodeToEmailReq(String email) {
    this.email = email;
  }
}
