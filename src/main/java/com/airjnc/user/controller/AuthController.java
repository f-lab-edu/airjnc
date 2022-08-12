package com.airjnc.user.controller;

import com.airjnc.common.annotation.CheckAuth;
import com.airjnc.user.dto.request.AuthLogInReq;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.service.AuthService;
import com.airjnc.user.service.UserStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  private final UserStateService userStateService;

  @PostMapping("/logIn")
  public UserResp logIn(@Validated @RequestBody AuthLogInReq authLogInReq) {
    return authService.logIn(authLogInReq);
  }

  @GetMapping("/logOut")
  @CheckAuth
  public void logOut() {
    userStateService.delete();
  }
}
