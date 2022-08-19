package com.airjnc.room.dao.impl;

import com.airjnc.common.exception.NotFoundException;
import com.airjnc.room.dao.RoomMapper;
import com.airjnc.room.dao.RoomRepository;
import com.airjnc.room.dto.response.RoomDetailResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisRoomRepository implements RoomRepository {

  private final RoomMapper roomMapper;

  @Override
  public RoomDetailResp findById(Long id) {
    return roomMapper.findById(id).orElseThrow(NotFoundException::new);
  }
}
