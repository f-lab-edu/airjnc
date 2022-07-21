package com.airjnc.user.service;

import com.airjnc.common.util.constant.SessionKey;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionUserStateService implements UserStateService {

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
