package com.airjnc.user.dto.response;

import com.airjnc.user.domain.Gender;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDTO {

  private final Long id;

  private final String email;

  private final String name;

  private final Gender gender;

  private final String phoneNumber;

  private final String address;

  private final LocalDate birthDate;

  @Builder
  public UserDTO(Long id, String email, String name, Gender gender, String phoneNumber, String address,
      LocalDate birthDate) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.gender = gender;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.birthDate = birthDate;
  }
}
