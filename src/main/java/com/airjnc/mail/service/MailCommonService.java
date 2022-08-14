package com.airjnc.mail.service;

import com.airjnc.common.dao.CommonRedisDao;
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

  private final CommonRedisDao commonRedisDao;

  private final SessionTtlProperties sessionTtlProperties;

  public void sendCode(String email, String userName) {
    String code = commonUtilService.generateCode();
    commonRedisDao.store(email, code, sessionTtlProperties.getCertificationCode());
    mailProvider.send(email, SendUsingTemplateDto.builder().name(userName).code(code).build());
  }
}
