package com.airjnc.admin.dto.request;

import com.airjnc.room.domain.RoomStatus;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateRoomReq {

  @NotNull
  private RoomStatus status;
}
