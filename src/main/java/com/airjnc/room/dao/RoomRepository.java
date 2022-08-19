package com.airjnc.room.dao;

import com.airjnc.room.dto.response.RoomDetailResp;

public interface RoomRepository {

  RoomDetailResp findById(Long id);
}
