package com.airjnc.user.dto.request;

import com.airjnc.user.domain.Gender;
import com.airjnc.user.util.UserRegex;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserCreateReq {

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

  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthDate;

  @Builder
  public UserCreateReq(String email, String password, String name, Gender gender, LocalDate birthDate) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.gender = gender;
    this.birthDate = birthDate;
  }
}
