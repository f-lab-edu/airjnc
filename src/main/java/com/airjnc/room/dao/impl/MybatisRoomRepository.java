package com.airjnc.room.dao.impl;

import com.airjnc.common.dto.Pageable;
import com.airjnc.room.dao.RoomMapper;
import com.airjnc.room.dao.RoomRepository;
import com.airjnc.room.domain.RoomStatus;
import com.airjnc.room.dto.response.SimpleRoom;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisRoomRepository implements RoomRepository {

  private final RoomMapper roomMapper;

  @Override
  public List<SimpleRoom> findAllByCategory(Long categoryId, RoomStatus roomStatus, Pageable pageable) {
    return roomMapper.findAllByCategory(categoryId, roomStatus, pageable);
  }
}
