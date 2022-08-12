package com.airjnc.user.dto;

import com.airjnc.common.exception.DefaultException;
import com.airjnc.common.util.factory.ErrorsFactory;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDto {

  private final String email;

  private final String phoneNumber;

  private final String name;

  private final LocalDate birthDate;

  private final UserStatus status;

  @Builder
  public UserDto(String email, String phoneNumber, String name, LocalDate birthDate, UserStatus status) {
    if (email == null && phoneNumber == null && name == null && birthDate == null) {
      throw new DefaultException(ErrorsFactory.createAndReject("dangerousQuery"));
    }
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.name = name;
    this.birthDate = birthDate;
    this.status = status;
  }

  public enum UserStatus {ACTIVE, DELETED, ALL}
}
