package com.airjnc.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.PasswordMatchDTO;
import com.airjnc.user.dto.request.LogInDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.util.UserModelMapper;
import com.airjnc.user.util.validator.PasswordMatchValidator;
import com.testutil.annotation.UnitTest;
import com.testutil.fixture.UserDTOFixture;
import com.testutil.fixture.UserEntityFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@UnitTest
class AuthServiceTest {

  @Mock
  UserModelMapper userModelMapper;

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
    given(userModelMapper.userEntityToUserDTO(userEntity)).willReturn(userDTO);
    //when
    UserDTO result = authService.logIn(logInDTO);
    //then
    then(userRepository).should(times(1)).findByEmail(logInDTO.getEmail());
    then(passwordMatchValidator).should(times(1)).validate(any(PasswordMatchDTO.class));
    then(userModelMapper).should(times(1)).userEntityToUserDTO(userEntity);
    assertThat(result).isSameAs(userDTO);
  }
}
