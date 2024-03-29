package com.airjnc.user.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.airjnc.common.interceptor.CheckAuthInterceptor;
import com.airjnc.common.resolver.CurrentUserIdArgumentResolver;
import com.airjnc.common.service.StateService;
import com.airjnc.common.util.enumerate.SessionKey;
import com.airjnc.user.dto.UserWhereDto;
import com.airjnc.user.dto.UserWhereDto.UserStatus;
import com.airjnc.user.dto.request.UserCreateReq;
import com.airjnc.user.dto.request.UserInquiryEmailReq;
import com.airjnc.user.dto.request.UserUpdateMyInfoReq;
import com.airjnc.user.dto.request.UserUpdateMyPasswordReq;
import com.airjnc.user.dto.request.UserUpdatePwdReq;
import com.airjnc.user.dto.response.UserInquiryEmailResp;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.service.UserAssembleService;
import com.airjnc.user.service.UserService;
import com.airjnc.user.util.UserModelMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testutil.annotation.IntegrationTest;
import com.testutil.fixture.user.UserCreateReqFixture;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(UserController.class)
@IntegrationTest
class UserControllerTest {

  @MockBean
  StateService stateService;

  @MockBean
  UserModelMapper userModelMapper;

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  UserService userService;

  @MockBean
  UserAssembleService userAssembleService;

  @SpyBean
  CurrentUserIdArgumentResolver currentUserIdArgumentResolver;

  @SpyBean
  CheckAuthInterceptor checkAuthInterceptor;

  private void checkInterceptorAndArgumentResolver() throws Exception {
    then(checkAuthInterceptor).should()
        .preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class), any(Object.class));
    then(currentUserIdArgumentResolver).should().resolveArgument(any(), any(), any(), any());
  }

  @Test
  void create() throws Exception {
    //given
    UserCreateReq userCreateReq = UserCreateReqFixture.getBuilder().build();
    UserResp userResp = UserRespFixture.getBuilder().build();
    given(userAssembleService.create(any(UserCreateReq.class))).willReturn(userResp);
    //when
    mockMvc.perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreateReq))
        ).andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("id").value(userResp.getId()));
    //then
    then(userAssembleService).should().create(any(UserCreateReq.class));
  }

  @Test
  void delete() throws Exception {
    //given
    Long userId = 1L;
    given(stateService.get(SessionKey.USER)).willReturn(userId);
    //when
    mockMvc.perform(
            MockMvcRequestBuilders.delete("/users/me")
        ).andDo(print())
        .andExpect(status().isNoContent());
    //then
    checkInterceptorAndArgumentResolver();
    then(userAssembleService).should().delete(userId);
  }

  @Test
  void getMyInfo() throws Exception {
    //given
    Long userId = 1L;
    UserResp userResp = UserRespFixture.getBuilder().id(userId).email(TestUser.EMAIL).build();
    given(stateService.get(SessionKey.USER)).willReturn(userId);
    given(userService.getUserById(userId, UserStatus.ACTIVE)).willReturn(userResp);
    //when
    mockMvc.perform(
            get("/users/me")
        ).andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("id").value(userId))
        .andExpect(jsonPath("email").value(TestUser.EMAIL));
    //then
    then(userService).should().getUserById(userId, UserStatus.ACTIVE);
    checkInterceptorAndArgumentResolver();
  }

  @Test
  void inquiryEmail() throws Exception {
    //given
    UserInquiryEmailReq req = UserInquiryEmailReq.builder().name(TestUser.NAME).birthDate(TestUser.BIRTH_DATE).build();
    UserInquiryEmailResp resp = UserInquiryEmailResp.builder()
        .id(TestUser.ID).email(TestUser.EMAIL).deletedAt(TestUser.CREATED_AT).build();
    UserResp userResp = UserRespFixture.getBuilder().build();
    given(userService.getUserByWhere(any(UserWhereDto.class))).willReturn(userResp);
    given(userModelMapper.userRespToUserInquiryEmailResp(userResp)).willReturn(resp);
    //when
    mockMvc.perform(
            get("/users/inquiryEmail")
                .param("name", req.getName())
                .param("birthDate", req.getBirthDate().toString())
        ).andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("id").value(resp.getId()))
        .andExpect(jsonPath("email").value(resp.getEmail()));
    //then
    then(userService).should().getUserByWhere(any(UserWhereDto.class));
    then(userModelMapper).should().userRespToUserInquiryEmailResp(userResp);
  }

  @Test
  void restore() throws Exception {
    //given
    Long userId = 1L;
    given(stateService.get(SessionKey.USER)).willReturn(userId);
    //when
    mockMvc.perform(
            patch("/users/me")
                .param("type", "restore")
        ).andDo(print())
        .andExpect(status().isOk());
    //then
    then(userService).should().restore(userId);
    checkInterceptorAndArgumentResolver();
  }

  @Test
  void updateMyInfo() throws Exception {
    //given
    Long userId = TestUser.ID;
    UserUpdateMyInfoReq req = UserUpdateMyInfoReq.builder().build();
    UserResp userResp = UserRespFixture.getBuilder().build();
    given(stateService.get(SessionKey.USER)).willReturn(userId);
    given(userService.update(eq(userId), any(UserUpdateMyInfoReq.class))).willReturn(userResp);
    //when
    mockMvc.perform(
            patch("/users/me")
                .param("type", "info")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
        ).andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("id").value(userResp.getId()));
    //then
    then(userService).should().update(eq(userId), any(UserUpdateMyInfoReq.class));
    checkInterceptorAndArgumentResolver();
  }

  @Test
  void updateMyPassword() throws Exception {
    //given
    Long userId = TestUser.ID;
    UserUpdateMyPasswordReq userUpdateMyPasswordReq = UserUpdateMyPasswordReq.builder()
        .password(TestUser.PASSWORD).newPassword("q1w2e3r4t5!@").build();
    UserResp userResp = UserRespFixture.getBuilder().build();
    given(stateService.get(SessionKey.USER)).willReturn(userId);
    given(userService.updateMyPassword(eq(userId), any(UserUpdateMyPasswordReq.class))).willReturn(userResp);
    //when
    mockMvc.perform(
            patch("/users/me")
                .param("type", "info")
                .param("what", "password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userUpdateMyPasswordReq))
        ).andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("id").value(userResp.getId()))
        .andExpect(jsonPath("email").value(userResp.getEmail()));
    //then
    then(userService).should().updateMyPassword(eq(userId), any(UserUpdateMyPasswordReq.class));
    checkInterceptorAndArgumentResolver();
  }

  @Test
  void updatePassword() throws Exception {
    //given
    UserUpdatePwdReq userUpdatePwdReq = UserUpdatePwdReq.builder()
        .email("test@naver.com")
        .certificationCode("123456")
        .password("q1w2e3r4t5!")
        .build();
    //when
    mockMvc.perform(
            patch("/users")
                .param("type", "info")
                .param("what", "password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userUpdatePwdReq))
        ).andDo(print())
        .andExpect(status().isOk());
    //then
    then(userService).should().updatePassword(any(UserUpdatePwdReq.class));
  }
}
