package com.airjnc.user.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInquiryEmailResp {

  private final Long id;

  private final String email;

  private final LocalDateTime deletedAt;

  @Builder
  public UserInquiryEmailResp(Long id, String email, LocalDateTime deletedAt) {
    this.id = id;
    this.email = email;
    this.deletedAt = deletedAt;
  }
}
