package com.airjnc.room.dao;

import com.airjnc.common.dto.Pageable;
import com.airjnc.room.domain.RoomStatus;
import com.airjnc.room.dto.response.SimpleRoom;
import java.util.List;

public interface RoomRepository {

  List<SimpleRoom> findAllByCategory(Long categoryId, RoomStatus roomStatus, Pageable pageable);
}
