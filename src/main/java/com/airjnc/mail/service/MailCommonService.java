package com.airjnc.mail.service;

import com.airjnc.common.dao.RedisDao;
import com.airjnc.common.properties.SessionTtlProperties;
import com.airjnc.common.service.CommonUtilService;
import com.airjnc.mail.dto.SendUsingTemplateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailCommonService {

  private final MailProvider mailProvider;

  private final CommonUtilService commonUtilService;

  private final SessionTtlProperties sessionTtlProperties;

  private final RedisDao redisDao;

  public void sendCode(String email, String userName) {
    String certificationCode = commonUtilService.generateCertificationCode();
    redisDao.store(email, certificationCode, sessionTtlProperties.getCertificationCode());
    mailProvider.send(email, SendUsingTemplateDto.builder().name(userName).certificationCode(certificationCode).build());
  }
}
