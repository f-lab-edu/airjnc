package com.airjnc.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.airjnc.common.aspect.Advice;
import com.airjnc.user.dto.request.UserLogInReq;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.service.AuthService;
import com.airjnc.user.service.UserStateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testutil.annotation.AopTest;
import com.testutil.fixture.LogInDTOFixture;
import com.testutil.fixture.UserDTOFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthController.class)
@AopTest
class AuthControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  AuthService authService;

  @MockBean
  UserStateService userStateService;

  @SpyBean
  Advice advice;

  @Test
  void logIn() throws Exception {
    //given
    UserLogInReq userLogInReq = LogInDTOFixture.getBuilder().build();
    UserResp userResp = UserDTOFixture.getBuilder().build();
    given(authService.logIn(any(UserLogInReq.class))).willReturn(userResp);
    //when
    mockMvc.perform(
            post("/auth/logIn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLogInReq))
        ).andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("id").value(userResp.getId()));
    //then
    then(authService).should(times(1)).logIn(any(UserLogInReq.class));
    then(userStateService).should(times(1)).create(userResp.getId());
  }

  @Test
  void logOut() throws Exception {
    //when
    mockMvc.perform(
            get("/auth/logOut")
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
        .andExpect(status().isOk());
    //then
    then(advice).should(times(1)).beforeCheckAuth();
    then(userStateService).should(times(1)).delete();
  }
}
