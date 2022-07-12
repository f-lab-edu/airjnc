package com.airjnc.common.resolver;

import com.airjnc.common.annotation.CurrentUserId;
import com.airjnc.common.exception.UnauthorizedException;
import com.airjnc.common.util.constant.SessionKey;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CurrentUserIdArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasCurrentUserAnnotation = parameter.hasParameterAnnotation(CurrentUserId.class);
        boolean hasLongType = Long.class.isAssignableFrom(parameter.getParameterType());
        return hasCurrentUserAnnotation && hasLongType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new UnauthorizedException();
        }
        return session.getAttribute(SessionKey.USER.name());
    }
}
