package com.airjnc.admin.dto.request;

import com.airjnc.room.domain.RoomStatus;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateRoomReq {

  @NotNull
  private RoomStatus status;

  @Builder

  public UpdateRoomReq(RoomStatus status) {
    this.status = status;
  }
}
