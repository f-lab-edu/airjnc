package com.airjnc.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import com.airjnc.common.service.StateService;
import com.airjnc.common.util.enumerate.SessionKey;
import com.airjnc.user.dto.request.UserCreateReq;
import com.airjnc.user.dto.response.UserResp;
import com.testutil.annotation.UnitTest;
import com.testutil.fixture.user.UserCreateReqFixture;
import com.testutil.fixture.user.UserRespFixture;
import com.testutil.testdata.TestUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class UserAssembleServiceTest {

  @Mock
  UserService userService;

  @Mock
  StateService stateService;

  @InjectMocks
  UserAssembleService userAssembleService;

  @Test
  void create() {
    //given
    UserCreateReq req = UserCreateReqFixture.getBuilder().build();
    UserResp userResp = UserRespFixture.getBuilder().build();
    given(userService.create(req)).willReturn(userResp);
    //when
    UserResp result = userAssembleService.create(req);
    //then
    then(userService).should(times(1)).create(req);
    then(stateService).should().create(SessionKey.USER, userResp.getId());
    assertThat(result.getId()).isEqualTo(userResp.getId());
  }

  @Test
  void delete() {
    //given
    Long userId = TestUser.ID;
    //when
    userAssembleService.delete(userId);
    //then
    then(userService).should(times(1)).delete(userId);
    then(stateService).should(times(1)).delete(SessionKey.USER);
  }
}
