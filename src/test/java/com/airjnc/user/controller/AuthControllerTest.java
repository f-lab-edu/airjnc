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
import com.airjnc.common.interceptor.CheckAuthInterceptor;
import com.airjnc.user.dto.request.AuthLogInReq;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.service.AuthService;
import com.airjnc.user.service.UserStateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testutil.annotation.IntegrationTest;
import com.testutil.fixture.AuthLogInReqFixture;
import com.testutil.fixture.UserRespFixture;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthController.class)
@IntegrationTest
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
  CheckAuthInterceptor checkAuthInterceptor;

  private void checkInterceptor(int n) throws Exception {
    then(checkAuthInterceptor).should(times(1))
        .preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class), any(Object.class));
    then(userStateService).should(times(n)).getUserId();
  }

  @Test
  void logIn() throws Exception {
    //given
    AuthLogInReq authLogInReq = AuthLogInReqFixture.getBuilder().build();
    UserResp userResp = UserRespFixture.getBuilder().build();
    given(authService.logIn(any(AuthLogInReq.class))).willReturn(userResp);
    //when
    mockMvc.perform(
            post("/auth/logIn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authLogInReq))
        ).andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("id").value(userResp.getId()));
    //then
    checkInterceptor(0);
    then(authService).should(times(1)).logIn(any(AuthLogInReq.class));
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
    checkInterceptor(1);
    then(userStateService).should(times(1)).delete();
  }
}
