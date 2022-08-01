package com.airjnc.user.service;

import com.airjnc.common.error.exception.DuplicateException;
import com.airjnc.common.util.BCryptHashEncoder;
import com.airjnc.user.domain.User;
import com.airjnc.user.dto.request.LogInRequestDTO;
import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.exception.UserLoginNotMatchException;
import com.airjnc.user.mapper.UserMapper;
import com.airjnc.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import util.UserFixture;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Spy
    UserMapper userMapper;

    @InjectMocks
    UserServiceImpl userServiceImpl;

    private User user;
    private UserDTO userDTO;
    private SignUpDTO signUpDTO;
    private LogInRequestDTO logInRequestDTO;
    private User encodePwdUser;


    @BeforeEach
    public void setUp() {

        this.user = UserFixture.getUserBuilder()
            .build();

        this.userDTO = UserFixture.getUserDTOBuilder()
            .build();

        this.signUpDTO = UserFixture.getSignUpDTOBuilder()
            .build();

        this.logInRequestDTO = UserFixture.getLogInRequestDTOBuilder()
            .build();

        this.encodePwdUser = UserFixture.getUserBuilder()
            .password(BCryptHashEncoder.encode(UserFixture.PASSWORD))
            .build();
    }

    @Test
    @DisplayName("회원가입시 이메일 중복 체크")
    public void whenDuplicateEmailExistsThenCustomException() throws Exception {
        //given
        BDDMockito.given(userRepository.selectUserByEmail(signUpDTO.getEmail()))
            .willReturn(Optional.of(user));

        // when, then\
        assertThrows(DuplicateException.class, () -> userServiceImpl.create(signUpDTO));
    }

    @Test
    @DisplayName("로그인 성공")
    public void successLogin() {
        //given
        BDDMockito.given(userRepository.selectUserByEmail(logInRequestDTO.getEmail()))
            .willReturn(Optional.of(encodePwdUser));

        // when, then
        assertDoesNotThrow(() -> userServiceImpl.logIn(logInRequestDTO));
    }

    @Test
    @DisplayName("로그인 실패_이메일미존재")
    public void whenNotMatchEmailThenFailLogin() {
        //given
        BDDMockito.given(userRepository.selectUserByEmail(logInRequestDTO.getEmail()))
            .willReturn(Optional.empty());

        // when, then
        assertThrows(UserLoginNotMatchException.class, () -> userServiceImpl.logIn(logInRequestDTO));
    }

    @Test
    @DisplayName("로그인 실패_비밀번호매칭실패")
    public void whenNotMatchPWDThenFailLogin() {
        //given
        User pwdNotMatchUser = UserFixture.getUserBuilder().password(BCryptHashEncoder.encode("notmypassword")).build();
        BDDMockito.given(userRepository.selectUserByEmail(logInRequestDTO.getEmail()))
            .willReturn(Optional.of(pwdNotMatchUser));

        // when, then
        assertThrows(UserLoginNotMatchException.class, () -> userServiceImpl.logIn(logInRequestDTO));
    }


}