package com.airjnc.user.dto.request;

import com.airjnc.user.util.UserRegex;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthLogInReq {

  @Email
  @NotBlank
  private String email;

  @NotBlank
  @Pattern(regexp = UserRegex.PASSWORD)
  private String password;

  @Builder
  public AuthLogInReq(String email, String password) {
    this.email = email;
    this.password = password;
  }
}
