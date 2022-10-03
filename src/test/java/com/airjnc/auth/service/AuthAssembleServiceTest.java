package com.airjnc.auth.service;

import com.airjnc.auth.dto.request.AuthLogInReq;
import com.airjnc.common.service.StateService;
import com.airjnc.common.util.enumerate.SessionKey;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.service.UserService;
import com.testutil.annotation.UnitTest;
import com.testutil.fixture.user.UserRespFixture;
import com.testutil.testdata.TestUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@UnitTest
@ExtendWith(MockitoExtension.class)
class AuthAssembleServiceTest {

  @Mock
  UserService userService;

  @Mock
  StateService stateService;

  @InjectMocks
  AuthAssembleService authAssembleService;

  @Nested
  @DisplayName("로그인")
  class LogIn {

    AuthLogInReq req;

    @BeforeEach
    void beforeEach() {
      req = AuthLogInReq.builder().email(TestUser.EMAIL).password(TestUser.PASSWORD).build();
    }

    void commonValidate(UserResp userResp, UserResp result, int callNumber) {
      then(userService).should().getUserByEmailAndPassword(req.getEmail(), req.getPassword());
      then(stateService).should(times(callNumber)).create(SessionKey.USER, userResp.getId());
      assertThat(result.getId()).isEqualTo(userResp.getId());
    }

    @Test
    @DisplayName("탈퇴한 유저라면, 해당 유저의 상태 정보를 만들면 안된다.")
    void whenUserIsDeletedThenDoNotCreateState() {
      //given
      UserResp userResp = UserRespFixture.getBuilder().id(TestUser.ID).deletedAt(LocalDateTime.now()).build();
      given(userService.getUserByEmailAndPassword(req.getEmail(), req.getPassword())).willReturn(userResp);
      //when
      UserResp result = authAssembleService.logIn(req);
      //then
      commonValidate(userResp, result, 0);
    }

    @Test
    @DisplayName("탈퇴하지 않은 유저라면, 해당 유저의 상태 정보를 만들어야 한다.")
    void whenUserIsNotDeletedThenCreateState() {
      //given
      UserResp userResp = UserRespFixture.getBuilder().id(TestUser.ID).deletedAt(null).build();
      given(userService.getUserByEmailAndPassword(req.getEmail(), req.getPassword())).willReturn(userResp);
      //when
      UserResp result = authAssembleService.logIn(req);
      //then
      commonValidate(userResp, result, 1);
    }
  }
}
