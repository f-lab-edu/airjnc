package com.airjnc.room.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.airjnc.room.dao.RoomMapper;
import com.airjnc.room.dao.RoomRepository;
import com.airjnc.room.dto.response.Room;
import com.testutil.annotation.MybatisTest;
import com.testutil.testdata.TestId;
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
  void findById() {
    //when
    Room room = roomRepository.findById(TestId.ROOM[0]);
    //then
    assertThat(room.getId()).isEqualTo(TestId.ROOM[0]);
  }
}
