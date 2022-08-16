package com.airjnc.common.service;

import com.airjnc.common.util.enumerate.SessionKey;

public interface StateService {

  void create(SessionKey sessionKey, Long userId);

  void delete(SessionKey sessionKey);

  Long getUserId();
}
