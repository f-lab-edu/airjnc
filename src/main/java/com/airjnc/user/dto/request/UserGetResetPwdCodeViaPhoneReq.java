package com.airjnc.user.dto.request;

import com.airjnc.user.util.UserRegex;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserGetResetPwdCodeViaPhoneReq {

  @NotBlank
  @Pattern(regexp = UserRegex.PHONE)
  private final String phone;

  public UserGetResetPwdCodeViaPhoneReq(String phone) {
    this.phone = phone;
  }
}
