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
import com.airjnc.common.aspect.Advice;
import com.airjnc.common.resolver.CurrentUserIdArgumentResolver;
import com.airjnc.user.dto.request.UserCreateReq;
import com.airjnc.user.dto.request.UserInquiryEmailReq;
import com.airjnc.user.dto.request.UserResetPwdReq;
import com.airjnc.user.dto.request.UserinquiryPasswordViaEmailReq;
import com.airjnc.user.dto.response.UserInquiryEmailResp;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.service.UserService;
import com.airjnc.user.service.UserStateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testutil.annotation.AopTest;
import com.testutil.fixture.CreateDTOFixture;
import com.testutil.fixture.UserDTOFixture;
import com.testutil.fixture.UserInquiryEmailReqDTOFixture;
import com.testutil.fixture.UserInquiryEmailResDTOFixture;
import com.testutil.testdata.TestUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(UserController.class)
@AopTest
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
  Advice advice;

  @SpyBean
  CurrentUserIdArgumentResolver currentUserIdArgumentResolver;

  @Test
  void create() throws Exception {
    //given
    UserCreateReq userCreateReq = CreateDTOFixture.getBuilder().build();
    UserResp userResp = UserDTOFixture.getBuilder().build();
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

  void checkArgumentResolverAndAspectAdvice() {
    // advice, argumentResolver가 정상적으로 적용되었는 지 테스트
    then(advice).should(times(1)).beforeCheckAuth();
    then(currentUserIdArgumentResolver).should(times(1))
        .resolveArgument(any(), any(), any(), any());
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
    checkArgumentResolverAndAspectAdvice();
    then(userService).should(times(1)).delete(userId);
    then(userStateService).should(times(1)).delete();
  }

  @Test
  void resetPassword() throws Exception {
    //given
    UserResetPwdReq userResetPwdReq = UserResetPwdReq.builder()
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
    then(userService).should(times(1)).inquiryPasswordViaEmail(any(UserinquiryPasswordViaEmailReq.class));
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
    checkArgumentResolverAndAspectAdvice();
  }
}
