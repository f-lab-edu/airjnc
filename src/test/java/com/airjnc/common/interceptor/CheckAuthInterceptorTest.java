package com.airjnc.common.interceptor;

import com.airjnc.common.annotation.CheckAuth;
import com.airjnc.common.exception.UnauthorizedException;
import com.airjnc.common.service.StateService;
import com.airjnc.common.util.enumerate.SessionKey;
import com.testutil.annotation.UnitTest;
import com.testutil.testdata.TestUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@UnitTest
class CheckAuthInterceptorTest {

  @Mock
  StateService stateService;

  @InjectMocks
  CheckAuthInterceptor checkAuthInterceptor;

  MockHttpServletRequest req;

  MockHttpServletResponse resp;

  @BeforeEach
  void beforeEach() {
    req = new MockHttpServletRequest();
    resp = new MockHttpServletResponse();
  }

  private HandlerMethod getHaveCheckAuthHandler() throws NoSuchMethodException {
    TestController testController = new TestController();
    Method method = TestController.class.getMethod("haveCheckAuth");
    return Mockito.spy(new HandlerMethod(testController, method));
  }

  private HandlerMethod getNoCheckAuthHandler() throws NoSuchMethodException {
    TestController testController = new TestController();
    Method method = TestController.class.getMethod("noCheckAuth");
    return Mockito.spy(new HandlerMethod(testController, method));
  }

  @Test
  @DisplayName("HandlerMethod가 null이라면, true를 반환해야 한다.")
  void givenNoHandlerMethodThenReturnTrue() throws Exception {
    //given
    Object handler = Mockito.spy(new Object());
    // when
    boolean result = checkAuthInterceptor.preHandle(req, resp, handler);
    //then
    assertThat(result).isTrue();
  }

  private static class TestController {

    @CheckAuth
    public void haveCheckAuth() {
    }

    public void noCheckAuth() {

    }
  }

  @Nested
  @DisplayName("HendlerMethod가 null이 아니라면")
  class GivenHandlerMethod {

    @Test
    @DisplayName("CheckAuth가 null이 아니고 세션에 userId가 없다면, UnauthorizedException을 던져야 한다.")
    void givenHaveCheckAuthAndNotStoreUserIdToStateThenThrowException() throws Exception {
      //given
      HandlerMethod handler = getHaveCheckAuthHandler();
      given(stateService.get(SessionKey.USER)).willReturn(null);
      //when
      assertThrows(
              UnauthorizedException.class,
              () -> checkAuthInterceptor.preHandle(req, resp, handler)
      );
      //then
      then(handler).should().getMethodAnnotation(CheckAuth.class);
      then(stateService).should().get(SessionKey.USER);
    }

    @Test
    @DisplayName("CheckAuth가 null이 아니고 세션에 userId가 저장되어 있다면, true를 반환해야 한다.")
    void givenHaveCheckAuthAndStoreUserIdToStateThenReturnTrue() throws Exception {
      //given
      Long userId = TestUser.ID;
      HandlerMethod handler = getHaveCheckAuthHandler();
      given(stateService.get(SessionKey.USER)).willReturn(userId);
      //when
      boolean result = checkAuthInterceptor.preHandle(req, resp, handler);
      //then
      then(handler).should().getMethodAnnotation(CheckAuth.class);
      then(stateService).should().get(SessionKey.USER);
      assertThat(req.getAttribute(CheckAuthInterceptor.AUTH_KEY)).isEqualTo(userId);
      assertThat(result).isTrue();
    }

    @Test
    @DisplayName("CheckAuth가 null이라면, true를 반환해야 한다.")
    void givenNoCheckAuthThenReturnTrue() throws Exception {
      //given
      HandlerMethod handler = getNoCheckAuthHandler();
      //when
      boolean result = checkAuthInterceptor.preHandle(req, resp, handler);
      //then
      then(handler).should().getMethodAnnotation(CheckAuth.class);
      assertThat(result).isTrue();
    }
  }
}
