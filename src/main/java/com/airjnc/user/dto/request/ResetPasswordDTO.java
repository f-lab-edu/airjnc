package com.airjnc.user.dto.request;

import com.airjnc.common.annotation.TwoFieldMatch;
import com.airjnc.user.util.UserRegex;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@TwoFieldMatch(first = "password", second = "passwordConfirm")
public class ResetPasswordDTO {

  @NotNull
  @Size(min = 6, max = 6)
  private String code;

  @NotNull
  @Pattern(regexp = UserRegex.PASSWORD)
  private String password;

  @NotNull
  private String passwordConfirm;

  @Builder
  public ResetPasswordDTO(String code, String password, String passwordConfirm) {
    this.code = code;
    this.password = password;
    this.passwordConfirm = passwordConfirm;
  }
}
