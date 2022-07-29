package com.airjnc.user.dto.response;

import com.airjnc.user.domain.Gender;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDTO {

  @NotNull
  private final Long id;

  @NotNull
  @Email
  private final String email;

  @NotNull
  private final String name;

  @NotNull
  private final Gender gender;

  private final String phoneNumber;

  private final String address;

  private final LocalDate birthDate;

  @Builder
  public UserDTO(Long id, String email, String name, Gender gender, String phoneNumber,
      String address,
      LocalDate birthDate) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.gender = gender;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.birthDate = birthDate;
  }
}
