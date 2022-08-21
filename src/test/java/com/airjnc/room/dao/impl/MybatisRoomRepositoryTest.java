package com.airjnc.room.dao.impl;

import com.airjnc.common.dto.Pageable;
import com.airjnc.room.dao.RoomMapper;
import com.airjnc.room.dao.RoomRepository;
import com.airjnc.room.domain.RoomStatus;
import com.airjnc.room.dto.response.SimpleRoom;
import com.testutil.annotation.MybatisTest;
import com.testutil.testdata.TestId;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@MybatisTest
class MybatisRoomRepositoryTest {

  @Autowired
  RoomMapper roomMapper;

  RoomRepository roomRepository;

  @BeforeEach
  void beforeEach() {
    roomRepository = new MybatisRoomRepository(roomMapper);
  }

  @Test
  void findAllByCategory() {
    //given
    RoomStatus roomStatus = RoomStatus.IN_OPERATION;
    Pageable pageable = Pageable.builder().skip(0).take(20).build();
    //when
    List<SimpleRoom> list = roomRepository.findAllByCategory(TestId.ROOM_CATEGORY[0], roomStatus, pageable);
    //then
  }
}
