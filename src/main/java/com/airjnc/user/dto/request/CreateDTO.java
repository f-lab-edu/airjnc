package com.airjnc.user.dto.request;

import com.airjnc.common.util.BCryptHashEncrypter;
import com.airjnc.common.util.CommonRegex;
import com.airjnc.user.domain.Gender;
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
  @Pattern(regexp = UserRegex.PASSWORD)
  private String password;

  @NotNull
  @Pattern(regexp = CommonRegex.localDate)
  private String birthDate;

  @Builder
  public CreateDTO(Long id, String email, String password, String name, Gender gender,
      String birthDate) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.name = name;
    this.gender = gender;
    this.birthDate = birthDate;
  }

  public void changePasswordToHash() {
    password = BCryptHashEncrypter.encrypt(this.password);
  }
}
