package com.airjnc.admin.service;

import static org.mockito.BDDMockito.then;

import com.airjnc.admin.dao.AdminRepository;
import com.airjnc.admin.dto.request.UpdateRoomReq;
import com.airjnc.room.dao.RoomRepository;
import com.airjnc.room.domain.RoomStatus;
import com.airjnc.room.dto.response.Room;
import com.testutil.annotation.UnitTest;
import com.testutil.testdata.TestId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

  @Mock
  AdminRepository adminRepository;

  @Mock
  RoomRepository roomRepository;

  @InjectMocks
  AdminService adminService;

  @Test
  public void updateRoom() {
    //given
    Long id = TestId.ROOM[0];
    UpdateRoomReq req = UpdateRoomReq.builder().status(RoomStatus.DISABLED).build();
    //when
    Room room = adminService.updateRoom(id, req);
    //then
    then(adminRepository).should().updateRoom(id, req.getStatus());
    then(roomRepository).should().findById(id);
  }
}
