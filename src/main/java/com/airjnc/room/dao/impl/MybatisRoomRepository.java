package com.airjnc.room.dao.impl;

import com.airjnc.room.dao.RoomMapper;
import com.airjnc.room.dao.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisRoomRepository implements RoomRepository {

  private final RoomMapper roomMapper;
}
