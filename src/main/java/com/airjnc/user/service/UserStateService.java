package com.airjnc.user.service;

public interface UserStateService {

  void create(Long userId);

  Long getUserId();

  void delete();
}
