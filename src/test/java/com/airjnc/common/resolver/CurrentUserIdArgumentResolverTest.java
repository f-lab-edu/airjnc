package com.airjnc.common.resolver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import com.airjnc.common.interceptor.CheckAuthInterceptor;
import com.testutil.annotation.UnitTest;
import com.testutil.testdata.TestUser;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

@ExtendWith(MockitoExtension.class)
@UnitTest
class CurrentUserIdArgumentResolverTest {

  CurrentUserIdArgumentResolver currentUserIdArgumentResolver;

  @Mock
  NativeWebRequest webRequest;

  @BeforeEach
  void beforeEach() {
    currentUserIdArgumentResolver = new CurrentUserIdArgumentResolver();
  }

  @Test
  void resolveArgument() {
    //given
    Long userId = TestUser.ID;
    MockHttpServletRequest req = new MockHttpServletRequest();
    given(webRequest.getNativeRequest(HttpServletRequest.class)).willReturn(req);
    req.setAttribute(CheckAuthInterceptor.AUTH_KEY, userId);
    //when
    Object result = currentUserIdArgumentResolver.resolveArgument(
        mock(MethodParameter.class), mock(ModelAndViewContainer.class), webRequest, mock(WebDataBinderFactory.class)
    );
    //then
    assertThat(result).isEqualTo(userId);
  }
}
