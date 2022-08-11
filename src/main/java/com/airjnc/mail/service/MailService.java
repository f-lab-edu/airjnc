package com.airjnc.mail.service;

import com.airjnc.mail.dto.request.MailSendCertificationCodeToEmailReq;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

  private final UserService userService;


  private final MailCommonService mailCommonService;

  public void sendCertificationCodeToEmail(Long userId) {
    UserResp user = userService.getUserById(userId);
    mailCommonService.sendCertificationCode(user.getEmail(), user.getName());
  }

  public void sendCertificationCodeToEmail(MailSendCertificationCodeToEmailReq req) {
    UserResp user = userService.getUserWithDeletedByEmail(req.getEmail());
    mailCommonService.sendCertificationCode(user.getEmail(), user.getName());
  }
}
