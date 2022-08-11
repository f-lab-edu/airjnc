package com.airjnc.mail.service;

import com.airjnc.common.dao.RedisDao;
import com.airjnc.common.properties.SessionTtlProperties;
import com.airjnc.common.service.CommonCertificationService;
import com.airjnc.mail.dto.SendUsingTemplateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailCommonService {

  private final CommonCertificationService commonCertificationService;

  private final MailProvider mailProvider;

  private final SessionTtlProperties sessionTtlProperties;

  private final RedisDao redisDao;

  public void sendCode(String email, String userName) {
    String code = commonCertificationService.generateCode();
    redisDao.store(code, email, sessionTtlProperties.getCertificationCode());
    mailProvider.send(email, SendUsingTemplateDto.builder().name(userName).code(code).build());
  }
}
