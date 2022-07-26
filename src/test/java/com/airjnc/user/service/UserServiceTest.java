package com.airjnc.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.spy;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.willDoNothing;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.CreateDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.util.UserModelMapper;
import com.testutil.annotation.UnitTest;
import com.testutil.fixture.CreateDTOFixture;
import com.testutil.fixture.UserDTOFixture;
import com.testutil.fixture.UserEntityFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@UnitTest
class UserServiceTest {

  @Mock
  UserRepository userRepository;

  @Mock
  UserCheckService userCheckService;

  @Mock
  UserModelMapper userModelMapper;

  @InjectMocks
  UserService userService;

  @Test()
  void userShouldBeCreated() {
    //given
    CreateDTO createDTO = spy(CreateDTOFixture.getBuilder().build());
    UserEntity userEntity = UserEntityFixture.getBuilder().build();
    UserDTO userDTO = UserDTOFixture.getBuilder().build();
    willDoNothing().given(createDTO).changePasswordToHash();
    given(userRepository.save(createDTO)).willReturn(userEntity);
    given(userModelMapper.userEntityToUserDTO(userEntity)).willReturn(userDTO);
    //when
    UserDTO result = userService.create(createDTO);
    //then
    then(userCheckService).should(times(1)).emailShouldNotBeDuplicated(createDTO.getEmail());
    then(createDTO).should(times(1)).changePasswordToHash();
    then(userRepository).should(times(1)).save(createDTO);
    then(userModelMapper).should(times(1)).userEntityToUserDTO(userEntity);
    assertThat(result.getId()).isEqualTo(userDTO.getId());
  }

  @Test
  void userShouldBeRemoved() {
    //given
    UserEntity userEntity = UserEntityFixture.getBuilder().build();
    //when
    userService.remove(userEntity.getId());
    //then
    then(userRepository).should(times(1)).remove(userEntity.getId());
  }
}

