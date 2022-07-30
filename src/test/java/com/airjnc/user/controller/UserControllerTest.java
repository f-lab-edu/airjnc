package com.airjnc.user.controller;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.airjnc.common.aspect.Advice;
import com.airjnc.common.resolver.CurrentUserIdArgumentResolver;
import com.airjnc.user.dto.request.UserCreateDTO;
import com.airjnc.user.dto.request.UserFindEmailDTO;
import com.airjnc.user.dto.request.UserResetPwdCodeViaEmailDTO;
import com.airjnc.user.dto.request.UserResetPwdCodeViaPhoneDTO;
import com.airjnc.user.dto.request.UserResetPwdDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.service.UserService;
import com.airjnc.user.service.UserStateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testutil.annotation.AopTest;
import com.testutil.fixture.CreateDTOFixture;
import com.testutil.fixture.FindEmailDTOFixture;
import com.testutil.fixture.UserDTOFixture;
import com.testutil.testdata.TestUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
  void findEmail() throws Exception {
    //given
    UserFindEmailDTO userFindEmailDTO = FindEmailDTOFixture.getBuilder().build();
    given(userService.findEmail(any(UserFindEmailDTO.class))).willReturn(TestUser.EMAIL);
    //when
    mockMvc.perform(
            get("/users/findEmail")
                .param("name", userFindEmailDTO.getName())
                .param("birthDate", userFindEmailDTO.getBirthDate())
        ).andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(TestUser.EMAIL));
    //then
    then(userService).should(times(1)).findEmail(any(UserFindEmailDTO.class));
  }

  @Test
  void resetPasswordCodeViaEmail() throws Exception {
    //given
    String email = TestUser.EMAIL;
    //when
    mockMvc.perform(
            get("/users/resetPassword")
                .param("via", "email")
                .param("email", email)
        ).andDo(print())
        .andExpect(status().isOk());
    //then
    then(userService).should(times(1)).resetPasswordViaEmail(any(UserResetPwdCodeViaEmailDTO.class));
  }

  @Test
  void resetPasswordCodeViaPhone() throws Exception {
    //given
    String phoneNumber = TestUser.PHONE_NUMBER;
    //when
    mockMvc.perform(
            get("/users/resetPassword")
                .param("via", "phone")
                .param("phoneNumber", phoneNumber)
        ).andDo(print())
        .andExpect(status().isOk());
    //then
    then(userService).should(times(1)).resetPasswordViaPhone(any(UserResetPwdCodeViaPhoneDTO.class));
  }

  @Test
  void create() throws Exception {
    //given
    UserCreateDTO userCreateDTO = CreateDTOFixture.getBuilder().build();
    UserDTO userDTO = UserDTOFixture.getBuilder().build();
    given(userService.create(any(UserCreateDTO.class))).willReturn(userDTO);
    //when
    mockMvc.perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreateDTO))
        ).andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("id").value(userDTO.getId()));
    //then
    then(userService).should(times(1)).create(any(UserCreateDTO.class));
    then(userStateService).should(times(1)).create(userDTO.getId());
  }

  @Test
  void resetPassword() throws Exception {
    //given
    UserResetPwdDTO userResetPwdDTO = UserResetPwdDTO.builder()
        .code("123456")
        .password("q1w2e3r4t5!")
        .passwordConfirm("q1w2e3r4t5!")
        .build();
    //when
    mockMvc.perform(
            put("/users/resetPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userResetPwdDTO))
        ).andDo(print())
        .andExpect(status().isOk());
    //then
    then(userService).should(times(1)).resetPassword(any(UserResetPwdDTO.class));
  }

  @Test
  void remove() throws Exception {
    //given
    Long userId = 1L;
    given(userStateService.getUserId()).willReturn(userId);
    //when
    mockMvc.perform(
            delete("/users/me")
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
        .andExpect(status().isNoContent());
    //then
    // advice, argumentResolver가 정상적으로 적용되었는 지 테스트
    then(advice).should(times(1)).beforeCheckAuth();
    then(currentUserIdArgumentResolver).should(times(1))
        .resolveArgument(any(), any(), any(), any());
    then(userService).should(times(1)).remove(userId);
    then(userStateService).should(times(1)).remove();
  }
}
