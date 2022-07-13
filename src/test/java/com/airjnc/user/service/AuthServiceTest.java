package com.airjnc.user.service;

import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.LogInDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.util.mapper.UserEntityMapper;
import com.airjnc.user.util.validator.PasswordMatchValidator;
import com.testutil.annotation.UnitTest;
import com.testutil.fixture.UserDTOFixture;
import com.testutil.fixture.UserEntityFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@UnitTest
class AuthServiceTest {
    @Mock
    UserEntityMapper userEntityMapper;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordMatchValidator passwordMatchValidator;
    @InjectMocks
    AuthService authService;

    @Test
    void userShouldBeLoggedIn() {
        //given
        LogInDTO logInDTO = mock(LogInDTO.class);
        UserEntity userEntity = UserEntityFixture.getBuilder().build();
        UserDTO userDTO = UserDTOFixture.getBuilder().build();
        given(userRepository.findByEmail(logInDTO.getEmail())).willReturn(userEntity);
        given(userEntityMapper.toUserDTO(userEntity)).willReturn(userDTO);
        //when
        UserDTO result = authService.logIn(logInDTO);
        //then
        then(userRepository).should(times(1)).findByEmail(logInDTO.getEmail());
        then(passwordMatchValidator).should(times(1)).validate(logInDTO.getPassword(), userEntity.getPassword());
        then(userEntityMapper).should(times(1)).toUserDTO(userEntity);
        assertThat(result).isSameAs(userDTO);
    }
}
