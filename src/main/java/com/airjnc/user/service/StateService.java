package com.airjnc.user.service;

public interface StateService {
    void create(Long userId);

    void remove();

    Long getUserId();
}
