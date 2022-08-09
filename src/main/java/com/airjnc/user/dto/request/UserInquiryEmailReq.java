package com.airjnc.user.dto.request;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
public class UserInquiryEmailReq {

  @NotBlank
  private final String name;

  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private final LocalDate birthDate;

  @Builder
  public UserInquiryEmailReq(String name, LocalDate birthDate) {
    this.name = name;
    this.birthDate = birthDate;
  }
}
