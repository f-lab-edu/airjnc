package com.airjnc.room.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.airjnc.common.service.StateService;
import com.airjnc.room.domain.RoomStatus;
import com.airjnc.room.dto.request.RoomGetAllReq;
import com.airjnc.room.service.RoomService;
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
  RoomService roomService;

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

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
}
