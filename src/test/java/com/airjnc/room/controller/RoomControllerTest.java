package com.airjnc.room.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.airjnc.common.dto.Pageable;
import com.airjnc.common.service.StateService;
import com.airjnc.room.dto.request.RoomGetAllReq;
import com.airjnc.room.dto.response.RoomSimpleResp;
import com.airjnc.room.service.RoomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testutil.annotation.IntegrationTest;
import com.testutil.fixture.room.RoomGetAllReqFixture;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
    RoomGetAllReq req = RoomGetAllReqFixture.getBuilder().build();
    given(roomService.getAll(any(RoomGetAllReq.class), any(Pageable.class)))
        .willReturn(List.of(new RoomSimpleResp()));
    //when
    ResultActions resultActions = mockMvc.perform(
        get("/rooms")
            .param("skip", "0")
            .param("take", "20")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(req))
    ).andDo(print());
    //then
    resultActions.andExpect(status().isOk())
        .andExpect(content().json("[{\"id\":null,\"title\":null,\"price\":null,\"thumbnailList\":null}]"));
    then(roomService).should().getAll(any(RoomGetAllReq.class), any(Pageable.class));
  }
}
