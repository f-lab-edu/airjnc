package com.airjnc.user.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserUpdateMyEmailReq {

  @NotBlank
  @Email
  private String newEmail;

  @NotBlank
  @Size(min = 6, max = 6)
  private String code;
}
