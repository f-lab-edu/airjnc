package com.airjnc.user.service;

import com.airjnc.common.service.StateService;
import com.airjnc.common.util.enumerate.SessionKey;
import com.airjnc.user.dto.request.UserCreateReq;
import com.airjnc.user.dto.response.UserResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 만약, 어떠한 기능이 2개 이상의 `Service` 들을 모아서 만들어야 한다면 `XXXAssembleService`를 이용한다. `XXXAssembleService`는 `XXXService` 를 의존할 수
 * 있으나, `XXXService`는 다른 `YYYService`를 의존할 수 없다. 순환성 참조를 막기 위해 이러한 규칙을 세웠습니다.
 */
@Service
@RequiredArgsConstructor
public class UserAssembleService {

  private final UserService userService;

  private final StateService stateService;

  public UserResp create(UserCreateReq req) {
    UserResp userResp = userService.create(req);
    stateService.create(SessionKey.USER, userResp.getId());
    return userResp;
  }

  public void delete(Long userId) {
    userService.delete(userId);
    stateService.delete(SessionKey.USER);
  }
}
