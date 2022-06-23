package com.airjnc.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class SessionAuthService implements AuthService {
    private final HttpSession httpSession;
    @Value("${session.expire}")
    private int expire;


    @Override
    public void logIn(AuthService.Key key, Long userId) {
        this.httpSession.setMaxInactiveInterval(this.expire);
        this.httpSession.setAttribute(key.name(), userId);
    }

    @Override
    public void logOut(AuthService.Key key) {
        this.httpSession.removeAttribute(key.name());
    }
}
