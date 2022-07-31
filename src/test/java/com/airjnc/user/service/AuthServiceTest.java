package com.airjnc.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.UserLogInReq;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.util.UserModelMapper;
import com.testutil.annotation.UnitTest;
import com.testutil.fixture.LogInDTOFixture;
import com.testutil.fixture.UserDTOFixture;
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

  @InjectMocks
  AuthService authService;

  @Test
  void userShouldBeLoggedIn() {
    //given
    UserLogInReq userLogInReq = LogInDTOFixture.getBuilder().build();
    UserEntity userEntity = TestUser.getBuilder().build();
    UserResp userResp = UserDTOFixture.getBuilder().build();
    given(userRepository.findByEmail(userLogInReq.getEmail())).willReturn(userEntity);
    given(userModelMapper.userEntityToUserDTO(userEntity)).willReturn(userResp);
    //when
    UserResp result = authService.logIn(userLogInReq);
    //then
    then(userRepository).should(times(1)).findByEmail(userLogInReq.getEmail());
    then(userCheckService).should(times(1))
        .passwordShouldBeMatch(userLogInReq.getPassword(), userEntity.getPassword());
    then(userModelMapper).should(times(1)).userEntityToUserDTO(userEntity);
    assertThat(result).isSameAs(userResp);
  }
}