package com.airjnc.user.service;

import com.airjnc.common.util.constant.SessionKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final HttpSession httpSession;

    public void create(Long userId) {
        httpSession.setAttribute(SessionKey.USER.name(), userId);
    }

    public void remove() {
        httpSession.removeAttribute(SessionKey.USER.name());
    }
}
