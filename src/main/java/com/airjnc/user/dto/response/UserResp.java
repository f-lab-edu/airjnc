package com.airjnc.user.dto.response;

import com.airjnc.user.domain.Gender;
import com.airjnc.user.domain.UserRole;
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

  private final String address;

  private final LocalDate birthDate;

  private final LocalDateTime deletedAt;

  private final UserRole role;

  @Builder
  public UserResp(Long id, String email, String name, Gender gender, String address,
      LocalDate birthDate, LocalDateTime deletedAt, UserRole role) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.gender = gender;
    this.address = address;
    this.birthDate = birthDate;
    this.deletedAt = deletedAt;
    this.role = role;
  }

  public boolean isDeleted() {
    return deletedAt != null;
  }
}
