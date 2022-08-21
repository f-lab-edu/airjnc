package com.airjnc.user.dto.request;

import com.airjnc.user.util.UserRegex;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserUpdateMyPasswordReq {

  @NotBlank
  @Pattern(regexp = UserRegex.PASSWORD)
  private String password;

  @NotBlank
  @Pattern(regexp = UserRegex.PASSWORD)
  private String newPassword;

  @Builder
  public UserUpdateMyPasswordReq(String password, String newPassword) {
    this.password = password;
    this.newPassword = newPassword;
  }
}
