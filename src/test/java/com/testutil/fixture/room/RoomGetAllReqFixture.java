package com.testutil.fixture.room;

import com.airjnc.room.domain.RoomStatus;
import com.airjnc.room.dto.request.RoomGetAllReq;

public class RoomGetAllReqFixture {

  public static RoomGetAllReq.RoomGetAllReqBuilder getBuilder() {
    return RoomGetAllReq.builder().categoryId(1L).status(RoomStatus.IN_OPERATION);
  }
}
