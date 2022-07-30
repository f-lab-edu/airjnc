package com.airjnc.user.controller;

import com.airjnc.common.annotation.CheckAuth;
import com.airjnc.common.annotation.CurrentUserId;
import com.airjnc.user.dto.request.UserCreateDTO;
import com.airjnc.user.dto.request.UserFindEmailDTO;
import com.airjnc.user.dto.request.UserResetPwdCodeViaEmailDTO;
import com.airjnc.user.dto.request.UserResetPwdCodeViaPhoneDTO;
import com.airjnc.user.dto.request.UserResetPwdDTO;
import com.airjnc.user.dto.response.UserDTO;
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
  public UserDTO create(@Validated @RequestBody UserCreateDTO userCreateDTO) {
    UserDTO userDTO = userService.create(userCreateDTO);
    userStateService.create(userDTO.getId());
    return userDTO;
  }

  @GetMapping("/findEmail")
  public String findEmail(@Validated @ModelAttribute UserFindEmailDTO userFindEmailDTO) {
    return userService.findEmail(userFindEmailDTO);
  }

  @DeleteMapping("/me")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @CheckAuth
  public void remove(@CurrentUserId Long currentUserId) {
    userService.remove(currentUserId);
    userStateService.remove();
  }

  @PutMapping("/resetPassword")
  public void resetPassword(@Validated @RequestBody UserResetPwdDTO userResetPwdDTO) {
    userService.resetPassword(userResetPwdDTO);
  }

  @GetMapping(value = "/resetPassword", params = "via=email")
  public void resetPasswordCodeViaEmail(
      @Validated @ModelAttribute UserResetPwdCodeViaEmailDTO userResetPwdCodeViaEmailDTO) {
    userService.resetPasswordViaEmail(userResetPwdCodeViaEmailDTO);
  }

  @GetMapping(value = "/resetPassword", params = "via=phone")
  public void resetPasswordCodeViaPhone(
      @Validated @ModelAttribute UserResetPwdCodeViaPhoneDTO userResetPwdCodeViaPhoneDTO) {
    userService.resetPasswordViaPhone(userResetPwdCodeViaPhoneDTO);
  }
}
