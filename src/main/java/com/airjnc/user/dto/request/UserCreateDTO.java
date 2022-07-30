package com.airjnc.user.dto.request;

import com.airjnc.common.util.CommonRegex;
import com.airjnc.user.dto.UserSaveDTO;
import com.airjnc.user.domain.Gender;
import com.airjnc.user.util.UserRegex;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserCreateDTO {

  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String name;

  @NotNull
  private Gender gender;

  @NotBlank
  @Pattern(regexp = UserRegex.PASSWORD)
  private String password;

  @NotBlank
  @Pattern(regexp = CommonRegex.localDate)
  private String birthDate;

  @Builder
  public UserCreateDTO(String email, String password, String name, Gender gender, String birthDate) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.gender = gender;
    this.birthDate = birthDate;
  }

  public UserSaveDTO toSaveDTO(String hash) {
    return UserSaveDTO.builder()
        .email(email)
        .name(name)
        .gender(gender)
        .password(hash)
        .birthDate(birthDate)
        .build();
  }
}
