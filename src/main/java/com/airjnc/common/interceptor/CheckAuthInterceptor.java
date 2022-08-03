package com.airjnc.common.interceptor;

import com.airjnc.common.annotation.CheckAuth;
import com.airjnc.common.exception.UnauthorizedException;
import com.airjnc.user.service.UserStateService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

// 1. HTTP REQUEST -> WAS -> Servlet Filter -> Interceptor(preHandle) -> Controller
// 2. Controller -> Interceptor(postHandle) -> Servlet Filter -> WAS -> HTTP RESPONSE
// * 뷰 렌더링 이후 `afterCompletion` 호출
@Component
@RequiredArgsConstructor
public class CheckAuthInterceptor implements HandlerInterceptor {

  public static final String AUTH_KEY = "currentUserId";

  private final UserStateService userStateService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    if (!(handler instanceof HandlerMethod)) {
      return true;
    }
    HandlerMethod handlerMethod = (HandlerMethod) handler;
    CheckAuth checkAuth = handlerMethod.getMethodAnnotation(CheckAuth.class);
    if (checkAuth == null) {
      return true;
    }
    Long userId = userStateService.getUserId();
    if (userId == null) {
      throw new UnauthorizedException();
    }
    request.setAttribute(AUTH_KEY, userId);
    return true;
  }
}
