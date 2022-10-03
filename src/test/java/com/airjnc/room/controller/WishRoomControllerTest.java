package com.airjnc.room.controller;

import com.airjnc.common.interceptor.CheckAuthInterceptor;
import com.airjnc.common.resolver.CurrentUserIdArgumentResolver;
import com.airjnc.common.service.StateService;
import com.airjnc.room.service.WishRoomService;
import com.airjnc.user.service.UserService;
import com.testutil.annotation.IntegrationTest;
import com.testutil.testdata.TestId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
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
}
