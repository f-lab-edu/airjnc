package com.airjnc.user.dto;

import com.airjnc.user.domain.Gender;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserSaveDto {

  private final Long id;

  private final String email;

  private final String name;

  private final Gender gender;

  private final String password;

  private final LocalDate birthDate;

  @Builder
  public UserSaveDto(Long id, String email, String name, Gender gender, String password, LocalDate birthDate) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.gender = gender;
    this.password = password;
    this.birthDate = birthDate;
  }
}
