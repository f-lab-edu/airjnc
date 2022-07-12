package com.airjnc.user.service;

import com.airjnc.common.util.constant.SessionKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class SessionStateService implements StateService {
    private final HttpSession httpSession;

    public void create(Long userId) {
        httpSession.setAttribute(SessionKey.USER.name(), userId);
    }

    public void remove() {
        httpSession.removeAttribute(SessionKey.USER.name());
    }

    public Long getUserId() {
        return (Long) httpSession.getAttribute(SessionKey.USER.name());
    }
}
