package com.airjnc.user.controller;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.airjnc.common.aspect.Advice;
import com.airjnc.common.resolver.CurrentUserIdArgumentResolver;
import com.airjnc.user.dto.request.CreateDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.service.UserService;
import com.airjnc.user.service.UserStateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testutil.annotation.AopTest;
import com.testutil.fixture.CreateDTOFixture;
import com.testutil.fixture.UserDTOFixture;
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
  void create() throws Exception {
    //given
    CreateDTO createDTO = CreateDTOFixture.getBuilder().build();
    UserDTO userDTO = UserDTOFixture.getBuilder().build();
    given(userService.create(any(CreateDTO.class))).willReturn(userDTO);
    //when
    mockMvc.perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO))
        ).andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("id").value(userDTO.getId()));
    //then
    then(userService).should(times(1)).create(any(CreateDTO.class));
    then(userStateService).should(times(1)).create(userDTO.getId());
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
    // advice, argumentResolver??? ??????????????? ??????????????? ??? ?????????
    then(advice).should(times(1)).beforeCheckAuth();
    then(currentUserIdArgumentResolver).should(times(1))
        .resolveArgument(any(), any(), any(), any());
    then(userService).should(times(1)).remove(userId);
    then(userStateService).should(times(1)).remove();
  }
}

