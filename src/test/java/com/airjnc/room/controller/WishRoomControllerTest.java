package com.airjnc.room.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.airjnc.common.service.StateService;
import com.airjnc.room.service.WishRoomService;
import com.testutil.annotation.IntegrationTest;
import com.testutil.testdata.TestId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(WishRoomController.class)
@IntegrationTest
class WishRoomControllerTest {

  @MockBean
  StateService stateService;

  @MockBean
  WishRoomService wishRoomService;

  @Autowired
  MockMvc mockMvc;

  @Test
  void create() throws Exception {
    //given
    long roomId = 1L;
    //when
    mockMvc.perform(
            post("/wishRoom/" + roomId)
        ).andDo(print())
        .andExpect(status().isCreated());
    //then
    then(wishRoomService).should().create(anyLong(), eq(roomId));
  }

  @Test
  void delete() throws Exception {
    //given
    long wishRoomId = TestId.WISH_ROOM;
    //when
    mockMvc.perform(
            MockMvcRequestBuilders.delete("/wishRoom/" + wishRoomId)
        ).andDo(print())
        .andExpect(status().isNoContent());
    //then
    then(wishRoomService).should().delete(anyLong(), eq(wishRoomId));
  }
}
