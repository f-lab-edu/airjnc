package com.airjnc.common.service;

import com.airjnc.common.util.enumerate.SessionKey;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionStateService implements StateService {

  private final HttpSession httpSession;

  public void create(SessionKey sessionKey, Long userId) {
    httpSession.setAttribute(sessionKey.name(), userId);
  }

  public void delete(SessionKey sessionKey) {
    httpSession.removeAttribute(sessionKey.name());
  }

  public Long getUserId() {
    return (Long) httpSession.getAttribute(SessionKey.USER.name());
  }
}
