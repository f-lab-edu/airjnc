package com.airjnc.user.controller;

import com.airjnc.common.util.constant.SessionKey;
import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.exception.DuplicatedEmailException;
import com.airjnc.user.service.AuthService;
import com.airjnc.user.service.UserService;
import com.airjnc.util.fixture.SignUpDTOFixture;
import com.airjnc.util.fixture.UserDTOFixture;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    UserService userService;
    @MockBean
    AuthService authService;


    String objToString(Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }

    @Test
    void whenEmailIsNotDuplicatedThenSuccessfullyCreatedAnd204StatusCode() throws Exception {
        //given
        SignUpDTO signUpDTO = SignUpDTOFixture.getBuilder().build();
        UserDTO userDTO = UserDTOFixture.getBuilder().build();
        given(userService.create(any(SignUpDTO.class))).willReturn(userDTO);
        //when
        mockMvc.perform(
                post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objToString(signUpDTO))
            ).andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("id").value(userDTO.getId()));
        //then
        then(userService).should(times(1)).create(any(SignUpDTO.class));
        then(authService).should(times(1)).logIn(SessionKey.USER, userDTO.getId());
    }

    @Test
    void whenEmailIsDuplicatedThenFailureAnd409StatusCode() throws Exception {
        //given
        UserDTO userDTO = UserDTOFixture.getBuilder().build();
        SignUpDTO signUpDTO = SignUpDTOFixture.getBuilder().build();
        given(userService.create(any(SignUpDTO.class))).willThrow(DuplicatedEmailException.class);
        //when
        mockMvc.perform(
                post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objToString(signUpDTO))
            ).andDo(print())
            .andExpect(status().isConflict());
        //then
        then(userService).should(times(1)).create(any(SignUpDTO.class));
        then(authService).should(times(0)).logIn(SessionKey.USER, userDTO.getId());
    }

    @Test
    void whenAuthThenSuccessfullyRemovedAnd204StatusCode() throws Exception {
        //given
        Long userId = 1L;
        //when
        mockMvc.perform(
                delete("/users/me")
                    .sessionAttr(SessionKey.USER.name(), userId)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isNoContent());
        //then
        then(userService).should(times(1)).remove(userId);
        then(authService).should(times(1)).logOut(SessionKey.USER);
    }

    @Test
    void whenNoAuthThenFailureAnd401StatusCode() throws Exception {
        //when
        mockMvc.perform(
                delete("/users/me")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isUnauthorized());
        //then
        then(userService).should(times(0)).remove(anyLong());
        then(authService).should(times(0)).logOut(SessionKey.USER);
    }
}
