package com.airjnc.user.dto.request;

import com.airjnc.common.util.CommonRegex;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserFindEmailDTO {

  @NotBlank
  private final String name;

  @NotBlank
  @Pattern(regexp = CommonRegex.localDate)
  private final String birthDate;

  @Builder
  public UserFindEmailDTO(String name, String birthDate) {
    this.name = name;
    this.birthDate = birthDate;
  }
}
