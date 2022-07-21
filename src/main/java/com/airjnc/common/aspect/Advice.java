package com.airjnc.common.aspect;

import com.airjnc.common.exception.UnauthorizedException;
import com.airjnc.user.service.UserStateService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Component
@Aspect
@RequiredArgsConstructor
public class Advice {

  private final UserStateService userStateService;

  @Before("com.airjnc.common.aspect.PointCut.checkAuth()")
  public void beforeCheckAuth() {
    Long userId = userStateService.getUserId();
    if (userId == null) {
      throw new UnauthorizedException();
    }
  }
}
