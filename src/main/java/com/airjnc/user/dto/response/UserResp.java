package com.airjnc.user.dto.response;

import com.airjnc.user.domain.Gender;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResp {

  private final Long id;

  private final String email;

  private final String name;

  private final Gender gender;

  private final String phoneNumber;

  private final String address;

  private final boolean isActive;

  private final LocalDate birthDate;

  @Builder
  public UserResp(Long id, String email, String name, Gender gender, String phoneNumber, String address,
      boolean isActive, LocalDate birthDate) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.gender = gender;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.isActive = isActive;
    this.birthDate = birthDate;
  }
}
