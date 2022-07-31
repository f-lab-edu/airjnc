package com.airjnc.integration;

import com.airjnc.user.controller.UserController;
import com.airjnc.user.dto.request.LogInRequestDTO;
import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import util.UserFixture;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SignUpIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserController userController;


    private UserDTO userDTO;
    private SignUpDTO signUpDTO;
    private SignUpDTO invalidSignUpDTO;
    private LogInRequestDTO logInRequestDTO;


    @BeforeEach
    public void setUp() {
        this.userDTO = UserFixture.getUserDTOBuilder()
            .id(null)
            .build();
        this.signUpDTO = UserFixture.getSignUpDTOBuilder()
            .build();
        this.invalidSignUpDTO = UserFixture.getSignUpDTOBuilder()
            .email("just_id")
            .password("asdferqewrasdfasdf")
            .name(null)
            .gender(null)
            .phoneNumber(null)
            .address(null)
            .birthDate(null)
            .build();
        this.logInRequestDTO = UserFixture.getLogInRequestDTOBuilder()
            .build();
    }

    @Test
    public void successSignUpAndFailSameEmail() throws Exception {
        // given
        String createUserJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(signUpDTO);

        // when, then // 회원가입 성공 201상태코드 및 회원가입 유저정보 반환
        mvc.perform(post("/user/signup").contentType(MediaType.APPLICATION_JSON).content(createUserJson))
            .andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.email").value(signUpDTO.getEmail()));

        // then // 생성된 유저 DB 저장여부 확인
        Assertions.assertThat(userRepository.selectUserByEmail(signUpDTO.getEmail()).isPresent()).isTrue();

        // then // 동일한 이메일로 회원가입시 실패
        mvc.perform(post("/user/signup").contentType(MediaType.APPLICATION_JSON).content(createUserJson))
            .andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void failSignUpWhenInvalidForm() throws Exception {
        // given
        String createUserJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(invalidSignUpDTO);

        // when, then // 회원가입 실패 400반환
        mvc.perform(post("/user/signup").contentType(MediaType.APPLICATION_JSON).content(createUserJson))
            .andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void successLogIn() throws Exception {
        // given
        String createUserJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(signUpDTO);
        String loginUserJson = new ObjectMapper().writeValueAsString(logInRequestDTO);

        // when 회원가입 성공 201상태코드 및 회원가입 유저정보 반환
        mvc.perform(post("/user/signup").contentType(MediaType.APPLICATION_JSON).content(createUserJson))
            .andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.email").value(signUpDTO.getEmail()));

        // then 로그인 성공
        mvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON).content(loginUserJson))
            .andDo(print())
            .andExpect(status().isOk());
    }


}
