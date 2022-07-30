package com.airjnc.user.dto.request;

import com.airjnc.user.util.UserRegex;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class ResetPasswordCodeViaPhoneDTO {

  @NotNull
  @Pattern(regexp = UserRegex.PHONE)
  private final String phoneNumber;

  public ResetPasswordCodeViaPhoneDTO(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
