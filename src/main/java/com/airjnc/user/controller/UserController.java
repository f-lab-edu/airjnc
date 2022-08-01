package com.airjnc.user.controller;

import com.airjnc.common.annotation.CheckAuth;
import com.airjnc.common.annotation.CurrentUserId;
import com.airjnc.user.dto.request.UserCreateReq;
import com.airjnc.user.dto.request.UserGetResetPwdCodeViaEmailReq;
import com.airjnc.user.dto.request.UserGetResetPwdCodeViaPhoneReq;
import com.airjnc.user.dto.request.UserInquiryEmailReq;
import com.airjnc.user.dto.request.UserResetPwdReq;
import com.airjnc.user.dto.response.UserInquiryEmailResp;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.service.UserService;
import com.airjnc.user.service.UserStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
  public UserResp create(@Validated @RequestBody UserCreateReq userCreateReq) {
    UserResp userResp = userService.create(userCreateReq);
    userStateService.create(userResp.getId());
    return userResp;
  }

  @DeleteMapping("/me")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @CheckAuth
  public void delete(@CurrentUserId Long currentUserId) {
    userService.delete(currentUserId);
    userStateService.delete();
  }

  @GetMapping("/inquiryEmail")
  public UserInquiryEmailResp findEmail(@Validated @ModelAttribute UserInquiryEmailReq userInquiryEmailReq) {
    return userService.inquiryEmail(userInquiryEmailReq);
  }

  @GetMapping(value = "/resetPassword", params = "email")
  public void getResetPwdCodeViaEmail(
      @Validated @ModelAttribute UserGetResetPwdCodeViaEmailReq userGetResetPwdCodeViaEmailReq) {
    userService.getResetPwdCodeViaEmail(userGetResetPwdCodeViaEmailReq);
  }

  // Naver에서 발신번호 등록이 계속해서 안되고 있어서, 일단 보류함
//  @GetMapping(value = "/resetPassword", params = "phone")
  public void getResetPwdCodeViaPhone(
      @Validated @ModelAttribute UserGetResetPwdCodeViaPhoneReq userGetResetPwdCodeViaPhoneReq) {
    userService.getResetPwdCodeViaPhone(userGetResetPwdCodeViaPhoneReq);
  }

  @PutMapping("/resetPassword")
  public void resetPassword(@Validated @RequestBody UserResetPwdReq userResetPwdReq) {
    userService.resetPassword(userResetPwdReq);
  }

  @PutMapping(value = "/me", params = "type=restore")
  @CheckAuth
  public void restore(@CurrentUserId Long userId) {
    userService.restore(userId);
  }
}
