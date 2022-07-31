package com.airjnc.common.auth.controller;

import com.airjnc.common.auth.dto.AuthInfoDTO;
import com.airjnc.common.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Optional;

@RestControllerAdvice
@RequiredArgsConstructor
public class AuthResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private final AuthService authService;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Optional<AuthInfoDTO> authInfo = Optional.ofNullable(authService.getAuthInfo());

        if (authInfo.isPresent()) {
            response.getHeaders().add(HttpHeaders.AUTHORIZATION, authInfo.toString());
        } else {
            response.getHeaders().add(HttpHeaders.AUTHORIZATION, AuthInfoDTO.builder().name("비회원입니다.").build().toString());
        }

        return body;

    }
}

