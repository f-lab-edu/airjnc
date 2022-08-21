package com.airjnc.room.dto.request;

import com.airjnc.room.domain.RoomStatus;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RoomGetAllReq {

  @NotNull
  @Min(1L)
  private final Long categoryId;

  @NotNull
  private final RoomStatus status;

  @Builder
  public RoomGetAllReq(Long categoryId, RoomStatus status) {
    this.categoryId = categoryId;
    this.status = status;
  }
}
