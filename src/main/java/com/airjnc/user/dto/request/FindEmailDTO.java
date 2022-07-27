package com.airjnc.user.dto.request;

import com.airjnc.common.util.CommonRegex;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FindEmailDTO {

  @NotNull
  private final String name;

  @Pattern(regexp = CommonRegex.localDate)
  private final String birthDate;
}
