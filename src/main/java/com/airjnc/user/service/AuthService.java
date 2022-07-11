package com.airjnc.user.service;


import com.airjnc.common.util.constant.SessionKey;

public interface AuthService {
    void logIn(SessionKey key, Long userId);

    void logOut(SessionKey key);
}
