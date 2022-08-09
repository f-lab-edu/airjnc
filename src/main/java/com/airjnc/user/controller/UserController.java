package com.airjnc.user.controller;

import com.airjnc.common.annotation.CheckAuth;
import com.airjnc.common.annotation.CurrentUserId;
import com.airjnc.user.dto.request.UserCreateReq;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.service.UserService;
import com.airjnc.user.service.UserStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  private final UserStateService userStateService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserResp create(@RequestBody @Validated UserCreateReq userCreateReq) {
    UserResp userResp = userService.create(userCreateReq);
    userStateService.create(userResp.getId());
    return userResp;
  }

  @DeleteMapping("/me")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @CheckAuth
  public void remove(@CurrentUserId Long currentUserId) {
    userService.remove(currentUserId);
    userStateService.remove();
  }
}
