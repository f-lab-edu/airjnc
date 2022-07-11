package com.airjnc.user.controller;

import com.airjnc.common.util.constant.SessionKey;
import com.airjnc.user.dto.request.CreateDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.service.SessionService;
import com.airjnc.user.service.UserService;
import com.airjnc.util.fixture.CreateDTOFixture;
import com.airjnc.util.fixture.UserDTOFixture;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
    SessionService sessionService;

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
        then(sessionService).should(times(1)).create(userDTO.getId());
    }

    @Test
    void remove() throws Exception {
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
        then(sessionService).should(times(1)).remove();
    }
}
