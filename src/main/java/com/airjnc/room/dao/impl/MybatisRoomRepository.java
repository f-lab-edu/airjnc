package com.airjnc.room.dao.impl;

import com.airjnc.common.exception.NotFoundException;
import com.airjnc.room.dao.RoomMapper;
import com.airjnc.room.dao.RoomRepository;
import com.airjnc.room.domain.RoomStatus;
import com.airjnc.room.dto.FindAllByCategoryDto;
import com.airjnc.room.dto.response.Room;
import com.airjnc.room.dto.response.SimpleRoom;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisRoomRepository implements RoomRepository {

  private final RoomMapper roomMapper;

  public int count(Long categoryId, RoomStatus status) {
    return roomMapper.count(categoryId, status);
  }

  @Override
  public List<SimpleRoom> findAllByCategory(FindAllByCategoryDto dto) {
    return roomMapper.findAllByCategory(dto);
  }

  @Override
  public Room findById(Long id) {
    return roomMapper.findById(id).orElseThrow(NotFoundException::new);
  }
}
