package com.airjnc.user.controller;

import com.airjnc.common.annotation.CheckAuth;
import com.airjnc.common.annotation.CurrentUserId;
import com.airjnc.user.dto.request.UserCreateReq;
import com.airjnc.user.dto.request.UserInquiryEmailReq;
import com.airjnc.user.dto.request.UserResetPwdCodeViaEmailReq;
import com.airjnc.user.dto.request.UserResetPwdCodeViaPhoneReq;
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

  @GetMapping("/inquiryEmail")
  public UserInquiryEmailResp findEmail(@Validated @ModelAttribute UserInquiryEmailReq userInquiryEmailReq) {
    return userService.inquiryEmail(userInquiryEmailReq);
  }

  @DeleteMapping("/me")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @CheckAuth
  public void remove(@CurrentUserId Long currentUserId) {
    userService.remove(currentUserId);
    userStateService.remove();
  }

  @PutMapping("/resetPassword")
  public void resetPassword(@Validated @RequestBody UserResetPwdReq userResetPwdReq) {
    userService.resetPassword(userResetPwdReq);
  }

  @GetMapping(value = "/resetPassword", params = "via=email")
  public void resetPasswordCodeViaEmail(
      @Validated @ModelAttribute UserResetPwdCodeViaEmailReq userResetPwdCodeViaEmailReq) {
    userService.resetPasswordViaEmail(userResetPwdCodeViaEmailReq);
  }

  @GetMapping(value = "/resetPassword", params = "via=phone")
  public void resetPasswordCodeViaPhone(
      @Validated @ModelAttribute UserResetPwdCodeViaPhoneReq userResetPwdCodeViaPhoneReq) {
    userService.resetPasswordViaPhone(userResetPwdCodeViaPhoneReq);
  }
}
