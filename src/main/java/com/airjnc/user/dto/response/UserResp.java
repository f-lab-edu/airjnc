package com.airjnc.user.dto.response;

import com.airjnc.user.domain.Gender;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

  private final LocalDateTime deletedAt;

  @Builder
  public UserResp(Long id, String email, String name, Gender gender, String phoneNumber, String address,
      boolean isActive, LocalDate birthDate, LocalDateTime deletedAt) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.gender = gender;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.isActive = isActive;
    this.birthDate = birthDate;
    this.deletedAt = deletedAt;
  }
}
