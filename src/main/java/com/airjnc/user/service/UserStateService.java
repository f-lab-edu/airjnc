package com.airjnc.user.service;

public interface UserStateService {

  void create(Long userId);

  void delete();

  Long getUserId();
}
