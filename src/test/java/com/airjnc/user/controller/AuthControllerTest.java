package com.airjnc.user.controller;

import com.airjnc.common.aspect.Advice;
import com.airjnc.user.dto.request.LogInDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.service.AuthService;
import com.airjnc.user.service.StateService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    StateService stateService;
    @SpyBean
    Advice advice;

    @Test
    void logIn() throws Exception {
        //given
        LogInDTO logInDTO = LogInDTOFixture.getBuilder().build();
        UserDTO userDTO = UserDTOFixture.getBuilder().build();
        given(authService.logIn(any(LogInDTO.class))).willReturn(userDTO);
        //when
        mockMvc.perform(
                post("/auth/logIn")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(logInDTO))
            ).andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(userDTO.getId()));
        //then
        then(authService).should(times(1)).logIn(any(LogInDTO.class));
        then(stateService).should(times(1)).create(userDTO.getId());
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
        then(stateService).should(times(1)).remove();
    }
}
