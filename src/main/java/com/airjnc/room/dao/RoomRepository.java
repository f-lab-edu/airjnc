package com.airjnc.room.dao;

import com.airjnc.room.dto.response.Room;

public interface RoomRepository {

  Room findById(Long id);
}
