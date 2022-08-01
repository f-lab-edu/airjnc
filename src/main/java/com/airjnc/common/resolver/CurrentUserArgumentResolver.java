package com.airjnc.common.resolver;

import com.airjnc.common.annotation.CurrentUser;
import com.airjnc.common.auth.dto.AuthInfoDTO;
import com.airjnc.common.auth.service.AuthService;
import com.airjnc.common.error.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * CurrentUser Annotation에 대한 처리
 */
@Component
@RequiredArgsConstructor
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthService authService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isCurrentUserAnnotation = parameter.getParameterAnnotation(CurrentUser.class) != null;
        boolean isCurrentUserClass = AuthInfoDTO.class.equals(parameter.getParameterType());

        return isCurrentUserAnnotation && isCurrentUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        try {
            AuthInfoDTO authInfo = authService.getAuthInfo();
            return authInfo;
        } catch (Exception e) {
            throw new UnauthorizedException("Unathorized");
        }
    }
}
