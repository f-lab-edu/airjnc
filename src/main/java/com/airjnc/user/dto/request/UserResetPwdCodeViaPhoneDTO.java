package com.airjnc.user.dto.request;

import com.airjnc.user.util.UserRegex;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserResetPwdCodeViaPhoneDTO {

  @NotBlank
  @Pattern(regexp = UserRegex.PHONE)
  private final String phoneNumber;

  public UserResetPwdCodeViaPhoneDTO(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
