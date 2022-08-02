package com.airjnc.common.auth.service;

import com.airjnc.common.auth.dto.AuthInfoDTO;
import com.airjnc.common.util.constant.SessionKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class SessionAuthService implements AuthService {

    private final HttpSession session;

    @Override
    public AuthInfoDTO getAuthInfo() {
        return (AuthInfoDTO) session.getAttribute(SessionKey.USER.getKey());
    }

    @Override
    public void setAuthInfo(AuthInfoDTO authInfoDTO) {
        session.setAttribute(SessionKey.USER.getKey(), authInfoDTO);
    }

    @Override
    public void clearAuthInfo() {
        session.invalidate();
    }
}
