package com.airjnc.user.service;


public interface AuthService {
    enum Key {
        USER
    }

    void logIn(AuthService.Key key, Long userId);

    void logOut(AuthService.Key key);
}
