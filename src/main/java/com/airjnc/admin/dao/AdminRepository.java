package com.airjnc.admin.dao;

import com.airjnc.room.domain.RoomStatus;

public interface AdminRepository {

  void updateRoom(Long id, RoomStatus status);
}
