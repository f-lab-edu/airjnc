package com.airjnc.user.controller;

import com.airjnc.user.dto.request.LogInDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.service.AuthService;
import com.airjnc.user.service.SessionService;
import com.airjnc.util.fixture.LogInDTOFixture;
import com.airjnc.util.fixture.UserDTOFixture;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    AuthService authService;
    @MockBean
    SessionService sessionService;

    void logIn() throws Exception {
        //given
        LogInDTO logInDTO = LogInDTOFixture.getBuilder().build();
        UserDTO userDTO = UserDTOFixture.getBuilder().build();
        given(authService.logIn(logInDTO)).willReturn(userDTO);
        //when
        mockMvc.perform(
                delete("/auth/logIn")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(logInDTO))
            ).andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(userDTO.getId()));
        //then
        then(authService).should(times(1)).logIn(logInDTO);
        then(sessionService).should(times(1)).create(userDTO.getId());
    }

    void logOut() throws Exception {
        //when
        mockMvc.perform(
                delete("/auth/logIn")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isOk());
        //then
        then(authService).should(times(1)).logOut();
        then(sessionService).should(times(1)).remove();
    }
}
