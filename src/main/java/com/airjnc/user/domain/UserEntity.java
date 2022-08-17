package com.airjnc.user.domain;

import com.airjnc.common.entity.CommonTimeEntity;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class UserEntity extends CommonTimeEntity {

  private Long id;

  private String email;

  private String phoneNumber;

  private String password;

  private String name;

  private Gender gender;

  private String address;

  private Boolean isActive;

  private LocalDate birthDate;

  @Builder
  public UserEntity(Long id, String email, String phoneNumber, String password, String name, Gender gender,
      String address, LocalDate birthDate) {
    this.id = id;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.password = password;
    this.name = name;
    this.gender = gender;
    this.address = address;
    this.isActive = true;
    this.birthDate = birthDate;
  }
}
