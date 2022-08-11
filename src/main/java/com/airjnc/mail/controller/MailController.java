package com.airjnc.mail.controller;

import com.airjnc.common.annotation.CheckAuth;
import com.airjnc.common.annotation.CurrentUserId;
import com.airjnc.mail.dto.request.MailSendCertificationCodeToEmailReq;
import com.airjnc.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MailController {

  private final MailService mailService;

  @PostMapping(value = "/certificationCode", params = {"to=email", "isAuth=1"})
  @CheckAuth
  public void sendCertificationCodeToEmail(@CurrentUserId Long userId) {
    mailService.sendCertificationCodeToEmail(userId);
  }

  @PostMapping(value = "/certificationCode", params = {"to=email", "isAuth=0"})
  public void sendCertificationCodeToEmail(@Validated @RequestBody MailSendCertificationCodeToEmailReq req) {
    mailService.sendCertificationCodeToEmail(req);
  }
}
