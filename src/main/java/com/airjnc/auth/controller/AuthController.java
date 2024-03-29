package com.airjnc.auth.controller;

import com.airjnc.auth.dto.request.AuthLogInReq;
import com.airjnc.auth.service.AuthAssembleService;
import com.airjnc.common.annotation.CheckAuth;
import com.airjnc.common.service.StateService;
import com.airjnc.common.util.enumerate.SessionKey;
import com.airjnc.user.dto.response.UserResp;
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

  private final StateService stateService;

  private final AuthAssembleService authAssembleService;

  @PostMapping("/logIn")
  public UserResp logIn(@Validated @RequestBody AuthLogInReq req) {
    return authAssembleService.logIn(req);
  }

  @GetMapping("/logOut")
  @CheckAuth
  public void logOut() {
    stateService.delete(SessionKey.USER);
  }
}
