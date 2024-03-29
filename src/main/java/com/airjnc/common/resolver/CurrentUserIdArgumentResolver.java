package com.airjnc.common.resolver;

import com.airjnc.common.annotation.CurrentUserId;
import com.airjnc.common.interceptor.CheckAuthInterceptor;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@Component
@RequiredArgsConstructor
public class CurrentUserIdArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
    HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
    return Objects.requireNonNull(servletRequest).getAttribute(CheckAuthInterceptor.AUTH_KEY);
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    boolean hasCurrentUserAnnotation = parameter.hasParameterAnnotation(CurrentUserId.class);
    boolean hasLongType = Long.class.isAssignableFrom(parameter.getParameterType());
    return hasCurrentUserAnnotation && hasLongType;
  }
}
