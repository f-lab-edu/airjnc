package com.airjnc.mail.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import com.airjnc.common.dao.RedisDao;
import com.airjnc.common.properties.SessionTtlProperties;
import com.airjnc.common.service.CommonUtilService;
import com.airjnc.mail.dto.SendUsingTemplateDto;
import com.testutil.annotation.UnitTest;
import com.testutil.testdata.TestUser;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class MailCommonServiceTest {

  @Mock
  CommonUtilService commonUtilService;

  @Mock
  MailProvider mailProvider;

  @Mock
  SessionTtlProperties sessionTtlProperties;

  @Mock
  RedisDao redisDao;

  @InjectMocks
  MailCommonService mailCommonService;

  @Test
  void sendCode() {
    //given
    String email = TestUser.EMAIL;
    String userName = TestUser.NAME;
    String code = "code";
    Duration ttl = Duration.of(3, ChronoUnit.MINUTES);
    given(commonUtilService.generateCertificationCode()).willReturn(code);
    given(sessionTtlProperties.getCertificationCode()).willReturn(ttl);
    //when
    mailCommonService.sendCode(email, userName);
    //then
    then(commonUtilService).should(times(1)).generateCertificationCode();
    then(redisDao).should().store(email, code, ttl);
    then(mailProvider).should().send(eq(email), any(SendUsingTemplateDto.class));
  }
}
