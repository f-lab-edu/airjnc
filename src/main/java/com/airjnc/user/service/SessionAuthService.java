package com.airjnc.user.service;

import com.airjnc.user.util.SessionKey;
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
    public void logIn(SessionKey key, Long userId) {
        httpSession.setMaxInactiveInterval(expire);
        httpSession.setAttribute(key.name(), userId);
    }

    @Override
    public void logOut(SessionKey key) {
        httpSession.removeAttribute(key.name());
    }
}
