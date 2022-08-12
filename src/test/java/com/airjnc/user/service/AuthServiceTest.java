package com.airjnc.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UserDto;
import com.airjnc.user.dto.request.AuthLogInReq;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.util.UserModelMapper;
import com.testutil.annotation.UnitTest;
import com.testutil.fixture.AuthLogInReqFixture;
import com.testutil.fixture.UserRespFixture;
import com.testutil.testdata.TestUser;
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
  UserCheckService userCheckService;

  @Mock
  UserStateService userStateService;

  @InjectMocks
  AuthService authService;

  @Test
  void userShouldBeLoggedIn() {
    //given
    AuthLogInReq authLogInReq = AuthLogInReqFixture.getBuilder().build();
    UserEntity userEntity = TestUser.getBuilder().build();
    UserResp userResp = UserRespFixture.getBuilder().build();

    given(userRepository.findByWhere(any(UserDto.class))).willReturn(userEntity);
    given(userModelMapper.userEntityToUserResp(userEntity)).willReturn(userResp);
    //when
    UserResp result = authService.logIn(authLogInReq);
    //then
    then(userRepository).should(times(1)).findByWhere(any(UserDto.class));
    then(userCheckService).should(times(1)).passwordShouldBeMatch(authLogInReq.getPassword(), userEntity.getPassword());
    then(userStateService).should(times(1)).create(userEntity.getId());
    then(userModelMapper).should(times(1)).userEntityToUserResp(userEntity);
    assertThat(result).isSameAs(userResp);
  }
}
