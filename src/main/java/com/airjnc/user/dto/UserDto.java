package com.airjnc.user.dto;

import com.airjnc.common.exception.DefaultException;
import com.airjnc.common.util.factory.ErrorsFactory;
import com.airjnc.user.domain.Gender;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDto {

  private final String email;

  private final String phoneNumber;

  private final String name;

  private final LocalDate birthDate;

  private final Gender gender;

  private final String address;

  private final UserStatus status;

  private final LocalDateTime deletedAt;

  @Builder
  public UserDto(String email, String phoneNumber, String name, LocalDate birthDate, Gender gender, String address,
      LocalDateTime deletedAt, UserStatus status) {
    if (email == null && phoneNumber == null && name == null && birthDate == null) {
      throw new DefaultException(ErrorsFactory.createAndReject("dangerousQuery"));
    }
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.name = name;
    this.birthDate = birthDate;
    this.gender = gender;
    this.address = address;
    this.status = status;
    this.deletedAt = deletedAt;
  }

  public enum UserStatus {ACTIVE, DELETED, ALL}
}
