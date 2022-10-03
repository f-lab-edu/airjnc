package com.airjnc.admin.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.airjnc.admin.dao.AdminMapper;
import com.airjnc.admin.dao.AdminRepository;
import com.airjnc.common.service.CommonValidateService;
import com.airjnc.room.dao.RoomMapper;
import com.airjnc.room.domain.RoomStatus;
import com.airjnc.room.dto.response.Room;
import com.testutil.annotation.MybatisTest;
import com.testutil.testdata.TestId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@MybatisTest
class MybatisAdminRepositoryTest {

  AdminRepository adminRepository;

  @Autowired
  AdminMapper adminMapper;

  @Autowired
  RoomMapper roomMapper;

  @Mock
  CommonValidateService commonValidateService;

  @BeforeEach
  void beforeEach() {
    adminRepository = new MybatisAdminRepository(adminMapper, commonValidateService);
  }

  @Test
  @Transactional
  public void updateRoom() {
    //given
    Long roomId = TestId.ROOM[0];
    Room room1 = roomMapper.findById(roomId).orElseThrow();
    //when
    adminRepository.updateRoom(TestId.ROOM[0], RoomStatus.DISABLED);
    Room room2 = roomMapper.findById(roomId).orElseThrow();
    //then
    assertThat(room1.getStatus()).isNotEqualTo(room2.getStatus());
    assertThat(room2.getStatus()).isEqualTo(RoomStatus.DISABLED);
  }
}
