package com.airjnc.user.controller;

import com.airjnc.common.annotation.CheckAuth;
import com.airjnc.common.annotation.CurrentUserId;
import com.airjnc.user.dto.UserWhereDto;
import com.airjnc.user.dto.UserWhereDto.UserStatus;
import com.airjnc.user.dto.request.UserCreateReq;
import com.airjnc.user.dto.request.UserInquiryEmailReq;
import com.airjnc.user.dto.request.UserUpdateMyEmailReq;
import com.airjnc.user.dto.request.UserUpdateMyInfoReq;
import com.airjnc.user.dto.request.UserUpdateMyPasswordReq;
import com.airjnc.user.dto.request.UserUpdatePwdReq;
import com.airjnc.user.dto.response.UserInquiryEmailResp;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.service.UserAssembleService;
import com.airjnc.user.service.UserService;
import com.airjnc.user.util.UserModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 1. Client의 요청을 어떻게 처리할지 정의하는곳 2. Client의 요청을 처리하고 어떻게 응답할지 결정하는 곳
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  private final UserAssembleService userAssembleService;

  private final UserModelMapper userModelMapper;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserResp create(@Validated @RequestBody UserCreateReq userCreateReq) {
    return userAssembleService.create(userCreateReq);
  }

  @DeleteMapping("/me")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @CheckAuth
  public void delete(@CurrentUserId Long userId) {
    userAssembleService.delete(userId);
  }

  @GetMapping("/me")
  @CheckAuth
  public UserResp getMyInfo(@CurrentUserId Long userId) {
    return userService.getUserById(userId, UserStatus.ACTIVE);
  }

  @GetMapping("/inquiryEmail")
  public UserInquiryEmailResp inquiryEmail(@Validated @ModelAttribute UserInquiryEmailReq req) {
    UserWhereDto userWhereDto = UserWhereDto.builder()
        .name(req.getName()).birthDate(req.getBirthDate()).status(UserStatus.ALL).build();
    UserResp userResp = userService.getUserByWhere(userWhereDto);
    return userModelMapper.userRespToUserInquiryEmailResp(userResp);
  }

  @PatchMapping(value = "/me", params = "type=restore")
  @CheckAuth
  public void restore(@CurrentUserId Long userId) {
    userService.restore(userId);
  }

  @PatchMapping(value = "me", params = {"type=info", "what=email"})
  @CheckAuth
  public UserResp updateMyEmail(@CurrentUserId Long userId,
      @Validated @RequestBody UserUpdateMyEmailReq userUpdateMyEmailReq) {
    return userService.updateMyEmail(userId, userUpdateMyEmailReq);
  }

  @PatchMapping(value = "/me", params = {"type=info"})
  @CheckAuth
  public UserResp updateMyInfo(@CurrentUserId Long userId, UserUpdateMyInfoReq req) {
    return userService.update(userId, req);
  }

  @PatchMapping(value = "/me", params = {"type=info", "what=password"})
  @CheckAuth
  public UserResp updateMyPassword(@CurrentUserId Long userId,
      @Validated @RequestBody UserUpdateMyPasswordReq userUpdateMyPasswordReq) {
    return userService.updateMyPassword(userId, userUpdateMyPasswordReq);
  }

  @PatchMapping(params = {"type=info", "what=password"})
  public void updatePassword(@Validated @RequestBody UserUpdatePwdReq userUpdatePwdReq) {
    userService.updatePassword(userUpdatePwdReq);
  }
}
