package com.airjnc.common.interceptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import com.airjnc.common.annotation.CheckAuth;
import com.airjnc.common.exception.UnauthorizedException;
import com.airjnc.user.service.UserStateService;
import com.testutil.annotation.UnitTest;
import java.lang.reflect.Method;
import org.junit.jupiter.api.BeforeEach;
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

@ExtendWith(MockitoExtension.class)
@UnitTest
class CheckAuthInterceptorTest {

  @Mock
  UserStateService userStateService;

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

  private Object getNothingHandler() {
    return Mockito.spy(new Object());
  }

  private static class TestController {

    @CheckAuth
    public void haveCheckAuth() {
    }

    public void noCheckAuth() {

    }
  }

  @Nested
  class GivenNoMethodHandler {

    @Test
    void thenReturnTrue() throws Exception {
      //given
      Object handler = getNothingHandler();
      // when
      boolean result = checkAuthInterceptor.preHandle(req, resp, handler);
      //then
      assertThat(result).isTrue();
    }
  }

  @Nested
  class givenMethodHandler {

    @Test
    void givenHaveCheckAuthAndNotStoreUserIdToStateThenThrowException() throws Exception {
      //given
      HandlerMethod handler = getHaveCheckAuthHandler();
      given(userStateService.getUserId()).willReturn(null);
      //when
      assertThrows(
          UnauthorizedException.class,
          () -> checkAuthInterceptor.preHandle(req, resp, handler)
      );
      //then
      then(handler).should(times(1)).getMethodAnnotation(CheckAuth.class);
      then(userStateService).should(times(1)).getUserId();
    }

    @Test
    void givenHaveCheckAuthAndStoreUserIdToStateThenReturnTrue() throws Exception {
      //given
      HandlerMethod handler = getHaveCheckAuthHandler();
      Long userId = 1L;
      given(userStateService.getUserId()).willReturn(userId);
      //when
      boolean result = checkAuthInterceptor.preHandle(req, resp, handler);
      //then
      then(handler).should(times(1)).getMethodAnnotation(CheckAuth.class);
      then(userStateService).should(times(1)).getUserId();
      assertThat(req.getAttribute(CheckAuthInterceptor.AUTH_KEY)).isEqualTo(userId);
      assertThat(result).isTrue();
    }

    @Test
    void givenNoCheckAuthThenReturnTrue() throws Exception {
      //given
      HandlerMethod handler = getNoCheckAuthHandler();
      //when
      boolean result = checkAuthInterceptor.preHandle(req, resp, handler);
      //then
      then(handler).should(times(1)).getMethodAnnotation(CheckAuth.class);
      assertThat(result).isTrue();
    }
  }
}