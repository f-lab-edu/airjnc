package com.airjnc.user.dto;

import com.airjnc.user.domain.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserSaveDTO {

  private final Long id;

  private final String email;

  private final String name;

  private final Gender gender;

  private final String password;

  private final String birthDate;

  @Builder
  public UserSaveDTO(Long id, String email, String name, Gender gender, String password, String birthDate) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.gender = gender;
    this.password = password;
    this.birthDate = birthDate;
  }
}
