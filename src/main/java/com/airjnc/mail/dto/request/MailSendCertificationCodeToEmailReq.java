package com.airjnc.mail.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MailSendCertificationCodeToEmailReq {

  @NotBlank
  @Email
  private String email;
}
