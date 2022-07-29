package com.airjnc.user.service;

import com.airjnc.common.error.exception.DuplicateException;
import com.airjnc.user.domain.User;
import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.dto.response.UserDTO;
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

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


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

    @BeforeEach
    public void setUp() {

        this.user = UserFixture.getUserBuilder()
            .build();

        this.userDTO = UserFixture.getUserDTOBuilder()
            .build();

        this.signUpDTO = UserFixture.getSignUpDTOBuilder()
            .build();
    }

    @Test
    @DisplayName("회원가입시 이메일 중복 체크")
    public void whenDuplicateEmailExistsThenCustomException() throws Exception {
        //given
        BDDMockito.given(userRepository.selectUserByEmail(signUpDTO.getEmail()))
            .willReturn(Optional.of(user));

        // when, then
        assertThatExceptionOfType(DuplicateException.class).isThrownBy(() -> userServiceImpl.create(signUpDTO));
    }


}