package com.airjnc.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.airjnc.auth.dto.request.AuthLogInReq;
import com.airjnc.common.service.StateService;
import com.airjnc.common.util.enumerate.SessionKey;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.service.UserService;
import com.testutil.annotation.UnitTest;
import com.testutil.fixture.user.UserRespFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class AuthAssembleServiceTest {

  @Mock
  UserService userService;

  @Mock
  StateService stateService;

  @InjectMocks
  AuthAssembleService authAssembleService;

  @Test
  void logIn() {
    //given
    UserResp userResp = UserRespFixture.getBuilder().build();
    AuthLogInReq req = AuthLogInReq.builder().build();
    given(userService.getUserByEmailAndPassword(req.getEmail(), req.getPassword())).willReturn(userResp);
    //when
    UserResp result = authAssembleService.logIn(req);
    //then
    then(userService).should().getUserByEmailAndPassword(req.getEmail(), req.getPassword());
    then(stateService).should().create(SessionKey.USER, userResp.getId());
    assertThat(result.getId()).isEqualTo(userResp.getId());
  }
}
