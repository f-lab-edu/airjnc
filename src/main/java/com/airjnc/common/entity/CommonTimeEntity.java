package com.airjnc.common.entity;

import java.time.Clock;
import java.time.LocalDateTime;

public class CommonTimeEntity {

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private LocalDateTime deletedAt;

  public void delete() {
    this.deletedAt = LocalDateTime.now(Clock.systemUTC());
  }

  public boolean isDeleted() {
    return deletedAt != null;
  }

  public void restore() {
    this.deletedAt = null;
  }
}
