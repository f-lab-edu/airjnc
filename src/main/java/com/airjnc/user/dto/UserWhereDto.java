package com.airjnc.user.dto;

import com.airjnc.common.exception.DefaultException;
import com.airjnc.common.util.factory.ErrorsFactory;
import com.airjnc.user.domain.Gender;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserWhereDto {

  private final Long id;

  private final String email;

  private final String name;

  private final Gender gender;

  private final String address;

  private final LocalDate birthDate;

  private final UserStatus status;

  @Builder
  public UserWhereDto(Long id, String email, String name, Gender gender, String address,
      LocalDate birthDate, UserStatus status) {
    if (id == null && email == null && name == null && birthDate == null) {
      throw new DefaultException(ErrorsFactory.createAndReject("dangerousQuery"));
    }
    this.id = id;
    this.email = email;
    this.name = name;
    this.gender = gender;
    this.address = address;
    this.birthDate = birthDate;
    this.status = status;
  }

  public enum UserStatus {ACTIVE, DELETED, ALL}
}
