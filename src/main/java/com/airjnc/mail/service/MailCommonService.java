package com.airjnc.mail.service;

import com.airjnc.common.service.CommonCertificationService;
import com.airjnc.mail.dto.SendUsingTemplateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailCommonService {

  private final CommonCertificationService commonCertificationService;

  private final MailProvider mailProvider;

  public void sendCode(String email, String userName) {
    String code = commonCertificationService.getCodeAndStoreToRedis(email);
    mailProvider.send(email, SendUsingTemplateDto.builder().name(userName).code(code).build());
  }
}
