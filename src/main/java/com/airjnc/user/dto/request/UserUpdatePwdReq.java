package com.airjnc.user.dto.request;

import com.airjnc.user.util.UserRegex;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserUpdatePwdReq {

  @NotBlank
  @Email
  private String email;

  @NotBlank
  @Size(min = 6, max = 6)
  private String certificationCode;

  @NotBlank
  @Pattern(regexp = UserRegex.PASSWORD)
  private String password;


  @Builder
  public UserUpdatePwdReq(String email, String certificationCode, String password) {
    this.email = email;
    this.certificationCode = certificationCode;
    this.password = password;
  }
}
