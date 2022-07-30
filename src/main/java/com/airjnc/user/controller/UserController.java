package com.airjnc.user.controller;

import com.airjnc.common.annotation.CheckAuth;
import com.airjnc.common.annotation.CurrentUserId;
import com.airjnc.user.dto.request.CreateDTO;
import com.airjnc.user.dto.request.FindEmailDTO;
import com.airjnc.user.dto.request.ResetPasswordCodeViaEmailDTO;
import com.airjnc.user.dto.request.ResetPasswordCodeViaPhoneDTO;
import com.airjnc.user.dto.request.ResetPasswordDTO;
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

  @GetMapping("/findEmail")
  public String findEmail(@Validated @ModelAttribute FindEmailDTO findEmailDTO) {
    return userService.findEmail(findEmailDTO);
  }

  @GetMapping(value = "/resetPassword", params = "via=email")
  public void resetPasswordCodeViaEmail(
      @Validated @ModelAttribute ResetPasswordCodeViaEmailDTO resetPasswordCodeViaEmailDTO) {
    userService.resetPasswordViaEmail(resetPasswordCodeViaEmailDTO);
  }

  @GetMapping(value = "/resetPassword", params = "via=phone")
  public void resetPasswordCodeViaPhone(
      @Validated @ModelAttribute ResetPasswordCodeViaPhoneDTO resetPasswordCodeViaPhoneDTO) {
    userService.resetPasswordViaPhone(resetPasswordCodeViaPhoneDTO);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserDTO create(@Validated @RequestBody CreateDTO createDTO) {
    UserDTO userDTO = userService.create(createDTO);
    userStateService.create(userDTO.getId());
    return userDTO;
  }

  @PutMapping("/resetPassword")
  public void resetPassword(@Validated @RequestBody ResetPasswordDTO resetPasswordDTO) {
    userService.resetPassword(resetPasswordDTO);
  }

  @DeleteMapping("/me")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @CheckAuth
  public void remove(@CurrentUserId Long currentUserId) {
    userService.remove(currentUserId);
    userStateService.remove();
  }
}
