package com.airjnc.room.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.airjnc.common.service.StateService;
import com.airjnc.room.dao.RoomRepository;
import com.airjnc.room.dto.response.RoomDetailResp;
import com.airjnc.room.service.RoomQueryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testutil.annotation.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
  RoomQueryService roomQueryService;

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  void getOne() throws Exception {
    //given
    Long id = 1L;
    RoomDetailResp roomDetailResp = RoomDetailResp.builder().id(id).build();
    given(roomRepository.findById(id)).willReturn(roomDetailResp);
    //when
    ResultActions resultActions = mockMvc.perform(
            get("/rooms/" + id)
        ).andDo(print())
        .andExpect(jsonPath("id").value(id));
    //then
    then(roomRepository).should().findById(id);
  }
}
