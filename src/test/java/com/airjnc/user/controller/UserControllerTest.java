package com.airjnc.user.controller;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.airjnc.common.interceptor.CheckAuthInterceptor;
import com.airjnc.common.resolver.CurrentUserIdArgumentResolver;
import com.airjnc.user.dto.request.UserCreateReq;
import com.airjnc.user.dto.request.UserInquiryEmailReq;
import com.airjnc.user.dto.request.UserInquiryPasswordViaEmailReq;
import com.airjnc.user.dto.request.UserResetPwdReq;
import com.airjnc.user.dto.response.UserInquiryEmailResp;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.service.UserService;
import com.airjnc.user.service.UserStateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testutil.fixture.UserCreateReqFixture;
import com.testutil.fixture.UserInquiryEmailReqDTOFixture;
import com.testutil.fixture.UserInquiryEmailResDTOFixture;
import com.testutil.fixture.UserRespFixture;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(UserController.class)
class UserControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  UserService userService;

  @MockBean
  UserStateService userStateService;

  @SpyBean
  CurrentUserIdArgumentResolver currentUserIdArgumentResolver;

  @SpyBean
  CheckAuthInterceptor checkAuthInterceptor;

  private void checkInterceptorAndArgumentResolver() throws Exception {
    then(checkAuthInterceptor).should(times(1))
        .preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class), any(Object.class));
    then(currentUserIdArgumentResolver).should(times(1))
        .resolveArgument(any(), any(), any(), any());
  }

  @Test
  void create() throws Exception {
    //given
    UserCreateReq userCreateReq = UserCreateReqFixture.getBuilder().build();
    UserResp userResp = UserRespFixture.getBuilder().build();
    given(userService.create(any(UserCreateReq.class))).willReturn(userResp);
    //when
    mockMvc.perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreateReq))
        ).andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("id").value(userResp.getId()));
    //then
    then(userService).should(times(1)).create(any(UserCreateReq.class));
    then(userStateService).should(times(1)).create(userResp.getId());
  }

  @Test
  void delete() throws Exception {
    //given
    Long userId = 1L;
    given(userStateService.getUserId()).willReturn(userId);
    //when
    mockMvc.perform(
            MockMvcRequestBuilders.delete("/users/me")
        ).andDo(print())
        .andExpect(status().isNoContent());
    //then
    checkInterceptorAndArgumentResolver();
    then(userService).should(times(1)).delete(userId);
    then(userStateService).should(times(1)).delete();
  }

  @Test
  void inquiryEmail() throws Exception {
    //given
    UserInquiryEmailReq userInquiryEmailReq = UserInquiryEmailReqDTOFixture.getBuilder().build();
    UserInquiryEmailResp userInquiryEmailResp = UserInquiryEmailResDTOFixture.getBuilder().build();
    given(userService.inquiryEmail(any(UserInquiryEmailReq.class))).willReturn(userInquiryEmailResp);
    //when
    mockMvc.perform(
            get("/users/inquiryEmail")
                .param("name", userInquiryEmailReq.getName())
                .param("birthDate", userInquiryEmailReq.getBirthDate().toString())
        ).andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("id").value(userInquiryEmailResp.getId()))
        .andExpect(jsonPath("email").value(userInquiryEmailResp.getEmail()));
    //then
    then(userService).should(times(1)).inquiryEmail(any(UserInquiryEmailReq.class));
  }

  @Test
  void inquiryPasswordViaEmail() throws Exception {
    //given
    String email = TestUser.EMAIL;
    //when
    mockMvc.perform(
            get("/users/inquiryPassword")
                .param("email", email)
        ).andDo(print())
        .andExpect(status().isOk());
    //then
    then(userService).should(times(1)).inquiryPasswordViaEmail(any(UserInquiryPasswordViaEmailReq.class));
  }

  @Test
  void resetPassword() throws Exception {
    //given
    UserResetPwdReq userResetPwdReq = UserResetPwdReq.builder()
        .email("test@naver.com")
        .code("123456")
        .password("q1w2e3r4t5!")
        .build();
    //when
    mockMvc.perform(
            put("/users/resetPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userResetPwdReq))
        ).andDo(print())
        .andExpect(status().isOk());
    //then
    then(userService).should(times(1)).resetPassword(any(UserResetPwdReq.class));
  }

  @Test
  void restore() throws Exception {
    //given
    Long userId = 1L;
    given(userStateService.getUserId()).willReturn(userId);
    //when
    mockMvc.perform(
            put("/users/me")
                .param("type", "restore")
        ).andDo(print())
        .andExpect(status().isOk());
    //then
    then(userService).should(times(1)).restore(userId);
    checkInterceptorAndArgumentResolver();
  }
}
