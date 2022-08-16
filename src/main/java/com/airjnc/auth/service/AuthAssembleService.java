package com.airjnc.auth.service;

import com.airjnc.auth.dto.request.AuthLogInReq;
import com.airjnc.common.service.StateService;
import com.airjnc.common.util.enumerate.SessionKey;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 만약, 어떠한 기능이 2개 이상의 `Service` 들을 모아서 만들어야 한다면 `XXXAssembleService`를 이용한다.
 * <p>
 * 1. `XXXAssembleService`는 `XXXService` 를 의존할 수 있으나, `XXXService`는 다른 `YYYService`를 의존할 수 없다. 순환성 참조를 막기 위해 이러한 규칙을
 * 세웠습니다.
 */
@Service
@RequiredArgsConstructor
public class AuthAssembleService {

  private final UserService userService;

  private final StateService stateService;

  public UserResp logIn(AuthLogInReq req) {
    UserResp userResp = userService.getUserByEmailAndPassword(req.getEmail(), req.getPassword());
    stateService.create(SessionKey.USER, userResp.getId());
    return userResp;
  }
}
