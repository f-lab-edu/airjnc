package com.airjnc.user.service;


import com.airjnc.user.util.SessionKey;

public interface AuthService {
    void logIn(SessionKey key, Long userId);

    void logOut(SessionKey key);
}
