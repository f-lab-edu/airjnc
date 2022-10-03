package com.airjnc.room.controller;

import com.airjnc.common.interceptor.CheckAuthInterceptor;
import com.airjnc.common.resolver.CurrentUserIdArgumentResolver;
import com.airjnc.common.service.StateService;
import com.airjnc.common.util.enumerate.SessionKey;
import com.airjnc.room.service.WishRoomService;
import com.airjnc.user.service.UserService;
import com.testutil.annotation.IntegrationTest;
import com.testutil.testdata.TestId;
import com.testutil.testdata.TestUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WishRoomController.class)
@IntegrationTest
class WishRoomControllerTest {

  @MockBean
  StateService stateService;

  @MockBean
  WishRoomService wishRoomService;

  @SpyBean
  CurrentUserIdArgumentResolver currentUserIdArgumentResolver;

  @SpyBean
  CheckAuthInterceptor checkAuthInterceptor;

  @MockBean
  UserService userService;

  @Autowired
  MockMvc mockMvc;

  @Test
  void delete() throws Exception {
    //given
    long wishRoomId = TestId.WISH_ROOM;
    //when
    mockMvc.perform(
                    MockMvcRequestBuilders.delete("/wishRooms/" + wishRoomId)
            ).andDo(print())
            .andExpect(status().isNoContent());
    //then
    then(wishRoomService).should().delete(anyLong(), eq(wishRoomId));
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
