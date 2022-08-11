package com.airjnc.user.dto;

import com.airjnc.user.domain.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserUpdateDto {

  private final Long id;

  private final String email;

  private final String name;

  private final Gender gender;

  private final String phoneNumber;

  private final String address;

  @Builder
  public UserUpdateDto(Long id, String email, String name, Gender gender, String phoneNumber, String address) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.gender = gender;
    this.phoneNumber = phoneNumber;
    this.address = address;
  }
}
