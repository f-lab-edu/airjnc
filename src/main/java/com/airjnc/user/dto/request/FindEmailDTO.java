package com.airjnc.user.dto.request;

import com.airjnc.common.util.CommonRegex;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FindEmailDTO {

  @NotNull
  private final String name;

  @Pattern(regexp = CommonRegex.localDate)
  private final String birthDate;

  @Builder
  public FindEmailDTO(String name, String birthDate) {
    this.name = name;
    this.birthDate = birthDate;
  }
}
