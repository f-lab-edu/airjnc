package com.airjnc.user.dto.request;

import com.airjnc.user.util.UserRegex;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResetPwdReq {

  @NotBlank
  @Size(min = 6, max = 6)
  private String code;

  @NotBlank
  @Pattern(regexp = UserRegex.PASSWORD)
  private String password;


  @Builder
  public UserResetPwdReq(String code, String password) {
    this.code = code;
    this.password = password;
  }
}
