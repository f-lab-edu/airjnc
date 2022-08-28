package com.airjnc.common.interceptor;

import com.airjnc.common.annotation.CheckAuth;
import com.airjnc.common.exception.ForbiddenException;
import com.airjnc.common.exception.UnauthorizedException;
import com.airjnc.common.service.StateService;
import com.airjnc.common.util.enumerate.SessionKey;
import com.airjnc.user.domain.UserRole;
import com.airjnc.user.dto.UserWhereDto.UserStatus;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.service.UserService;
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

  private final StateService stateService;

  private final UserService userService;

  private void checkRole(CheckAuth checkAuth, Long userId) {
    UserRole role = checkAuth.role();
    switch (role) {
      case DEFAULT:
        break;
      case ADMIN:
        UserResp user = userService.getUserById(userId, UserStatus.ACTIVE);
        if (user.getRole() != UserRole.ADMIN) {
          throw new ForbiddenException();
        }
        break;
      default:
        throw new UnsupportedOperationException();
    }
  }

  private HandlerMethod getHandlerMethod(Object handler) {
    if (!(handler instanceof HandlerMethod)) {
      return null;
    }
    return (HandlerMethod) handler;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    HandlerMethod handlerMethod = getHandlerMethod(handler);
    if (handlerMethod == null) {
      return true;
    }

    CheckAuth checkAuth = handlerMethod.getMethodAnnotation(CheckAuth.class);
    if (checkAuth == null) {
      return true;
    }

    Long userId = stateService.get(SessionKey.USER);
    if (userId == null) {
      throw new UnauthorizedException();
    }

    checkRole(checkAuth, userId);

    request.setAttribute(AUTH_KEY, userId);
    return true;
  }
}
