package com.airjnc.user.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserUpdateMyEmailReq {

  @NotBlank
  private String code;

  @NotBlank
  @Email
  private String newEmail;
}
