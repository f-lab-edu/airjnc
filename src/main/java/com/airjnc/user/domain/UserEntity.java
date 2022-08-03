package com.airjnc.user.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntity {

  private Long id;

  private String email;

  private String phoneNumber;

  private String password;

  private String name;

  private Gender gender;

  private String address;

  private LocalDate birthDate;

  private boolean isActive;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private LocalDateTime deletedAt;

  @Builder
  public UserEntity(Long id, String email, String password, String name, Gender gender, String phoneNumber,
      String address, boolean isActive, LocalDate birthDate, LocalDateTime createdAt, LocalDateTime updatedAt,
      LocalDateTime deletedAt) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.name = name;
    this.gender = gender;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.isActive = isActive;
    this.birthDate = birthDate;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.deletedAt = deletedAt;
  }

  public boolean isDeleted() {
    return deletedAt != null;
  }
}
