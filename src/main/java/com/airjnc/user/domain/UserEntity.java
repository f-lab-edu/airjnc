package com.airjnc.user.domain;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntity {

  private Long id;

  private String email;

  private String password;

  private String name;

  private Gender gender;

  private String address;

  private LocalDate birthDate;

  private LocalDateTime deletedAt;

  @Builder
  public UserEntity(Long id, String email, String password, String name, Gender gender,
      String address, LocalDate birthDate, LocalDateTime deletedAt) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.name = name;
    this.gender = gender;
    this.address = address;
    this.birthDate = birthDate;
    this.deletedAt = deletedAt;
  }

  public void delete() {
    this.deletedAt = LocalDateTime.now(Clock.systemUTC());
  }

  public boolean isDeleted() {
    return deletedAt != null;
  }

  public void restore() {
    this.deletedAt = null;
  }
}
