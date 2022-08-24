package com.airjnc.room.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.airjnc.room.dao.RoomMapper;
import com.airjnc.room.dao.RoomRepository;
import com.airjnc.room.domain.RoomStatus;
import com.airjnc.room.dto.FindAllByCategoryDto;
import com.airjnc.room.dto.response.Room;
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

  void count() {
    //when
    int count = roomRepository.count(TestId.ROOM_CATEGORY[0], RoomStatus.IN_OPERATION);
    //then
    assertThat(count).isEqualTo(2);
  }

  @Test
  void findAllByCategory() {
    //given
    FindAllByCategoryDto dto = FindAllByCategoryDto.builder()
        .categoryId(1L).roomStatus(RoomStatus.IN_OPERATION).skip(0L).offset(20L).build();
    //when
    List<SimpleRoom> list = roomRepository.findAllByCategory(dto);
    //then
  }

  @Test
  void findById() {
    //when
    Room room = roomRepository.findById(TestId.ROOM[0]);
    //then
    assertThat(room.getId()).isEqualTo(TestId.ROOM[0]);
  }
}
