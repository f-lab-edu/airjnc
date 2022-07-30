package com.airjnc.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import com.airjnc.common.util.enumerate.SessionKey;
import com.testutil.annotation.UnitTest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@UnitTest
class SessionUserStateServiceTest {

  @Mock
  HttpSession httpSession;

  @InjectMocks
  SessionUserStateService sessionStateService;

  @Test
  void sessionShouldBeRemoved() {
    //when
    sessionStateService.remove();
    //then
    then(httpSession).should(times(1)).removeAttribute(SessionKey.USER.name());
  }

  @Test
  void sessionShouldBeSaved() {
    //given
    Long userId = 1L;
    //when
    sessionStateService.create(userId);
    //then
    then(httpSession).should(times(1)).setAttribute(SessionKey.USER.name(), userId);
  }

  @Test
  void shouldReturnUserId() {
    //given
    Long userId = 1L;
    given(httpSession.getAttribute(SessionKey.USER.name())).willReturn(userId);
    //when
    Long returnUserId = sessionStateService.getUserId();
    //then
    then(httpSession).should(times(1)).getAttribute(SessionKey.USER.name());
    assertThat(returnUserId).isEqualTo(userId);
  }
}
