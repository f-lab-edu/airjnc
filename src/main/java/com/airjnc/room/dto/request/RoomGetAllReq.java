package com.airjnc.room.dto.request;

import com.airjnc.room.domain.RoomStatus;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomGetAllReq {

  @NotNull
  @Min(1L)
  private Long categoryId;

  @NotNull
  private RoomStatus status;

  @Builder
  public RoomGetAllReq(Long categoryId, RoomStatus status) {
    this.categoryId = categoryId;
    this.status = status;
  }
}
