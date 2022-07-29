package com.airjnc.user.dto.request;

import com.airjnc.common.util.BCryptHashEncrypter;
import com.airjnc.user.domain.Gender;
import com.airjnc.user.util.Regex;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateDTO {

  private Long id;

  @NotNull
  @Email
  private String email;

  @NotNull
  private String name;

  @NotNull
  private Gender gender;

  @NotNull
  @Pattern(regexp = Regex.Password.format)
  private String password;

  @Builder
  public CreateDTO(Long id, String email, String password, String name, Gender gender) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.name = name;
    this.gender = gender;
  }

  public void changePasswordToHash() {
    password = BCryptHashEncrypter.encrypt(this.password);
  }
}
