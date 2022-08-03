package com.airjnc.user.dto.request;

import com.airjnc.user.util.UserRegex;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogInDTO {

  @NotNull
  @Email
  private String email;

  @NotNull
  @Pattern(regexp = UserRegex.password)
  private String password;

  @Builder
  public LogInDTO(String email, String password) {
    this.email = email;
    this.password = password;
  }
}
