package com.airjnc.room.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.airjnc.common.service.StateService;
import com.airjnc.common.util.enumerate.SessionKey;
import com.airjnc.room.service.WishRoomService;
import com.testutil.annotation.IntegrationTest;
import com.testutil.testdata.TestUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

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
            post("/wishRooms/" + roomId)
        ).andDo(print())
        .andExpect(status().isCreated());
    //then
    then(wishRoomService).should().create(anyLong(), eq(roomId));
  }

  @Test
  void getMyWishRooms() throws Exception {
    //given
    given(stateService.get(SessionKey.USER)).willReturn(TestUser.ID);
    //when
    ResultActions resultActions = mockMvc.perform(
        get("/wishRooms")
            .param("page", "1")
            .param("size", "20")
    ).andDo(print());
    //then
    then(wishRoomService).should().getMyWishRooms(anyLong(), any(Pageable.class));
  }
}
