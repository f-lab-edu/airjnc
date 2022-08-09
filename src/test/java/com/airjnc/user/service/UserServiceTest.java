package com.airjnc.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.spy;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UserSaveDto;
import com.airjnc.user.dto.request.UserCreateReq;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.util.UserModelMapper;
import com.testutil.annotation.UnitTest;
import com.testutil.fixture.UserCreateReqFixture;
import com.testutil.fixture.UserRespFixture;
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
    UserCreateReq createDTO = spy(UserCreateReqFixture.getBuilder().build());
    UserEntity userEntity = UserEntityFixture.getBuilder().build();
    UserResp userDTO = UserRespFixture.getBuilder().build();
    given(userRepository.save(any(UserSaveDto.class))).willReturn(userEntity);
    given(userModelMapper.userEntityToUserDTO(userEntity)).willReturn(userDTO);
    //when
    UserResp result = userService.create(createDTO);
    //then
    then(userCheckService).should(times(1)).emailShouldNotBeDuplicated(createDTO.getEmail());
    then(userRepository).should(times(1)).save(any(UserSaveDto.class));
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

