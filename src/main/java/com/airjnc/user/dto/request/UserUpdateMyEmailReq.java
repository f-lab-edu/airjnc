package com.airjnc.user.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserUpdateMyEmailReq {

  @NotBlank
  @Size(min = 6, max = 6)
  private String certificationCode;

  @NotBlank
  @Email
  private String newEmail;
}
