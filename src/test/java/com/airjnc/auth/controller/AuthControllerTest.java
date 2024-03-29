package com.airjnc.auth.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.airjnc.auth.dto.request.AuthLogInReq;
import com.airjnc.auth.service.AuthAssembleService;
import com.airjnc.common.interceptor.CheckAuthInterceptor;
import com.airjnc.common.service.StateService;
import com.airjnc.common.util.enumerate.SessionKey;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testutil.annotation.IntegrationTest;
import com.testutil.fixture.user.UserRespFixture;
import com.testutil.testdata.TestUser;
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
  AuthAssembleService authAssembleService;

  @MockBean
  StateService stateService;

  @SpyBean
  CheckAuthInterceptor checkAuthInterceptor;

  @MockBean
  UserService userService;

  private void checkInterceptor(int n) throws Exception {
    then(checkAuthInterceptor).should()
        .preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class), any(Object.class));
    then(stateService).should(times(n)).get(SessionKey.USER);
  }

  @Test
  void logIn() throws Exception {
    //given
    AuthLogInReq req = AuthLogInReq.builder().email(TestUser.EMAIL).password(TestUser.PASSWORD).build();
    UserResp userResp = UserRespFixture.getBuilder().build();
    given(authAssembleService.logIn(any(AuthLogInReq.class))).willReturn(userResp);
    //when
    mockMvc.perform(
            post("/auth/logIn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
        ).andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("id").value(userResp.getId()));
    //then
    checkInterceptor(0);
    then(authAssembleService).should().logIn(any(AuthLogInReq.class));
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
    then(stateService).should().delete(SessionKey.USER);
  }
}
