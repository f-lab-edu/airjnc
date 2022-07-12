package com.airjnc.user.service;

import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.LogInDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.util.validator.PasswordMatchValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Spy
    ModelMapper modelMapper;
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
        UserEntity userEntity = mock(UserEntity.class);
        given(userRepository.findByEmail(logInDTO.getEmail())).willReturn(userEntity);
        //when
        UserDTO userDTO = authService.logIn(logInDTO);
        //then
        then(userRepository).should(times(1)).findByEmail(logInDTO.getEmail());
        then(passwordMatchValidator).should(times(1)).validate(logInDTO.getPassword(), userEntity.getPassword());
        then(modelMapper).should(times(1)).map(userEntity, UserDTO.class);
        assertThat(userDTO.getId()).isEqualTo(userEntity.getId());
    }

}
