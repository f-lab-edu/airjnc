package com.airjnc.admin.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.airjnc.admin.dto.request.UpdateRoomReq;
import com.airjnc.admin.service.AdminService;
import com.airjnc.common.service.StateService;
import com.airjnc.common.util.enumerate.SessionKey;
import com.airjnc.room.domain.RoomStatus;
import com.airjnc.user.domain.UserRole;
import com.airjnc.user.dto.UserWhereDto.UserStatus;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testutil.annotation.IntegrationTest;
import com.testutil.testdata.TestId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminController.class)
@IntegrationTest
class AdminControllerTest {

  @MockBean
  AdminService adminService;

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  StateService stateService;

  @MockBean
  UserService userService;

  @Test
  void updateRoom() throws Exception {
    //given
    Long roomId = TestId.ROOM[0];
    UpdateRoomReq req = UpdateRoomReq.builder().status(RoomStatus.DISABLED).build();
    Long userId = TestId.USER;
    UserResp user = UserResp.builder().id(userId).role(UserRole.ADMIN).build();
    given(stateService.get(SessionKey.USER)).willReturn(userId);
    given(userService.getUserById(userId, UserStatus.ACTIVE)).willReturn(user);
    //when
    mockMvc.perform(
            patch("/admin/rooms/" + roomId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
        ).andDo(print())
        .andExpect(status().isOk());
    //then
    then(adminService).should().updateRoom(eq(roomId), any(UpdateRoomReq.class));
  }
}
