package com.airjnc.user.domain;

import com.airjnc.user.dto.request.UserUpdateMyInfoReq;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

  private UserRole role;

  @Builder
  public UserEntity(Long id, String email, String password, String name, Gender gender,
      String address, LocalDate birthDate, LocalDateTime deletedAt, UserRole role) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.name = name;
    this.gender = gender;
    this.address = address;
    this.birthDate = birthDate;
    this.deletedAt = deletedAt;
    this.role = role;
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

  public void update(UserUpdateMyInfoReq req) {
    if (req.getName() != null) {
      name = req.getName();
    }
    if (req.getGender() != null) {
      gender = req.getGender();
    }
    if (req.getAddress() != null) {
      address = req.getAddress();
    }
    if (req.getBirthDate() != null) {
      birthDate = req.getBirthDate();
    }
  }
}
