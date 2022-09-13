package com.airjnc.room.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.airjnc.common.service.StateService;
import com.airjnc.room.dao.RoomRepository;
import com.airjnc.room.domain.RoomStatus;
import com.airjnc.room.dto.request.RoomGetAllReq;
import com.airjnc.room.dto.response.Room;
import com.airjnc.room.service.RoomService;
import com.airjnc.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testutil.annotation.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(RoomController.class)
@IntegrationTest
class RoomControllerTest {

  @MockBean
  StateService stateService;

  @MockBean
  RoomRepository roomRepository;

  @MockBean
  RoomService roomService;

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  UserService userService;

  @Test
  void getAll() throws Exception {
    //given
    //when
    ResultActions resultActions = mockMvc.perform(
        get("/rooms")
            .param("categoryId", "1")
            .param("status", RoomStatus.IN_OPERATION.name())
            .param("page", "1")
            .param("size", "20")
    ).andDo(print());
    //then
    then(roomService).should().getAll(any(RoomGetAllReq.class), any(Pageable.class));
  }

  @Test
  void getOne() throws Exception {
    //given
    Long id = 1L;
    Room room = Room.builder().id(id).build();
    given(roomRepository.findById(id)).willReturn(room);
    //when
    ResultActions resultActions = mockMvc.perform(
            get("/rooms/" + id)
        ).andDo(print())
        .andExpect(jsonPath("id").value(id));
    //then
    then(roomRepository).should().findById(id);
  }
}
