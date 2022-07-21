package com.airjnc.user.service;

public interface UserStateService {

  void create(Long userId);

  void remove();

  Long getUserId();
}
