package com.airjnc.user.controller;

import com.airjnc.common.auth.dto.AuthInfoDTO;
import com.airjnc.common.auth.service.AuthService;
import com.airjnc.common.auth.service.SessionAuthService;
import com.airjnc.user.dto.request.LogInRequestDTO;
import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.dto.response.FindPwdResponseDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.exception.UserLoginNotMatchException;
import com.airjnc.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.assertj.core.api.Assertions;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import util.UserFixture;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @SpyBean
    private SessionAuthService authService;

    @MockBean
    private UserService userService;
    

    private UserDTO userDTO;
    private SignUpDTO signUpDTO;
    private SignUpDTO invalidSignUpDTO;
    private LogInRequestDTO logInRequestDTO;
    private AuthInfoDTO authInfoDTO;

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

        this.authInfoDTO = UserFixture.getAuthInfoDTOBuilder()
            .build();
    }

    @Test
    @DisplayName("존재하는 이메일 입력 패스워드 찾음")
    public void whenExistsEmailThenFindPassword() throws Exception {
        //given
        FindPwdResponseDTO findPwdResponseDTO = FindPwdResponseDTO.builder()
            .email(userDTO.getEmail())
            .password(userDTO.getPassword())
            .build();
        BDDMockito.given(userService.findPasswordByEmail(userDTO.getEmail()))
            .willReturn(findPwdResponseDTO);

        //when , then
        mvc.perform(get("/user/login/findpassword").param("email", userDTO.getEmail()))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.password").value(findPwdResponseDTO.getPassword()));
    }

    @Test
    @DisplayName("비밀번호찾기시 유효성 검사")
    public void validateWhenFindPasswordThenReturnErrorMessage() throws Exception {
        //given
        String unValidEmailForm = "justId";
        //when, then
        mvc.perform(get("/user/login/findpassword").param("email", unValidEmailForm))
            .andDo(print())
            .andExpect(status().is4xxClientError());
    }


    @Test
    @DisplayName("회원가입 성공")
    public void whenJoinUserThenSuccessCreateUser() throws Exception {
        //given
        String createUserJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(signUpDTO);
        BDDMockito.given(userService.create(signUpDTO)).willReturn(userDTO);
        //when, then
        mvc.perform(post("/user/signup").contentType(MediaType.APPLICATION_JSON).content(createUserJson))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.email").value(userDTO.getEmail()));
    }

    @Test
    @DisplayName("회원가입시 유효성 검사")
    public void validateWhenSignUpThenReturnErrorMessage() throws Exception {
        //given
        String invalidSignUpJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(invalidSignUpDTO);

        //when, then
        mvc.perform(post("/user/signup").contentType(MediaType.APPLICATION_JSON).content(invalidSignUpJson))
            .andDo(print())
            .andExpect(status().is4xxClientError());
        ;
    }

    @Test
    @DisplayName("Locale별 ValidatorMessage 반환")
    public void givenLocaleThenReturnLocaleErrorMessage() throws Exception {
        //given
        SignUpDTO nameInvalidSignUpDTO = UserFixture.getSignUpDTOBuilder()
            .name(null)
            .build();
        String nameInvalidSignUpJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(nameInvalidSignUpDTO);

        //when, then us
        mvc.perform(post("/user/signup").locale(Locale.US).contentType(MediaType.APPLICATION_JSON).content(nameInvalidSignUpJson))
            .andDo(print())
            .andExpect(jsonPath("$.errors[0].reason").value("Please Enter Value"));
        //when, then kor
        mvc.perform(post("/user/signup").locale(Locale.KOREA).contentType(MediaType.APPLICATION_JSON).content(nameInvalidSignUpJson))
            .andDo(print())
            .andExpect(jsonPath("$.errors[0].reason").value("값을 입력해 주세요"));
    }

    @Test
    @DisplayName("Login 성공")
    public void successLogin() throws Exception {
        //given
        String logInUserJson = new ObjectMapper().writeValueAsString(logInRequestDTO);
        BDDMockito.given(userService.logIn(logInRequestDTO)).willReturn(authInfoDTO);

        //when, then
        mvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON).content(logInUserJson))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Login 실패")
    public void failLogin() throws Exception {
        //given
        String logInUserJson = new ObjectMapper().writeValueAsString(logInRequestDTO);
        BDDMockito.willThrow(new UserLoginNotMatchException()).given(userService).logIn(logInRequestDTO);

        //when, then
        mvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON).content(logInUserJson))
            .andDo(print())
            .andExpect(status().is4xxClientError());
    }
    
    @Test
    @DisplayName("Session 저장 확인")
    public void successSessionSave() throws Exception {
        //given
        String logInUserJson = new ObjectMapper().writeValueAsString(logInRequestDTO);
        BDDMockito.given(userService.logIn(logInRequestDTO)).willReturn(authInfoDTO);

        //when, then
        mvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON).content(logInUserJson))
            .andDo(print())
            .andExpect(status().isOk());

        assertThat(authService.getAuthInfo()).isEqualTo(authInfoDTO);
    }
    
    


}