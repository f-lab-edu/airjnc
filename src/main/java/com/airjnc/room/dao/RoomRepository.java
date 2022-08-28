package com.airjnc.room.dao;

import com.airjnc.room.domain.RoomStatus;
import com.airjnc.room.dto.FindAllByCategoryDto;
import com.airjnc.room.dto.response.Room;
import com.airjnc.room.dto.response.SimpleRoom;
import java.util.List;

public interface RoomRepository {

  int count(Long categoryId, RoomStatus status);

  List<SimpleRoom> findAllByCategory(FindAllByCategoryDto dto);

  List<SimpleRoom> findAllByUserId(Long userId, Long skip, Long offset);

  Room findById(Long id);
}
