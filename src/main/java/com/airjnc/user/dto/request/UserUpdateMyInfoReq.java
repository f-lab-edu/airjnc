package com.airjnc.user.dto.request;

import com.airjnc.user.domain.Gender;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserUpdateMyInfoReq {

  @NotBlank
  private String name;

  @NotBlank
  private Gender gender;

  @NotBlank
  private String address;

  @NotBlank
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthDate;

  @Builder
  public UserUpdateMyInfoReq(String name, Gender gender, String address, LocalDate birthDate) {
    this.name = name;
    this.gender = gender;
    this.address = address;
    this.birthDate = birthDate;
  }
}
