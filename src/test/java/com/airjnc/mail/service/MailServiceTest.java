package com.airjnc.mail.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import com.airjnc.mail.dto.request.MailSendCertificationCodeToEmailReq;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.service.UserService;
import com.testutil.annotation.UnitTest;
import com.testutil.fixture.UserRespFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class MailServiceTest {

  @Mock
  UserService userService;

  @Mock
  MailCommonService mailCommonService;

  @InjectMocks
  MailService mailService;

  UserResp user;

  @BeforeEach
  void beforeEach() {
    user = UserRespFixture.getBuilder().build();
  }

  @Test
  void sendCertificationCodeToEmail() {
    Long userId = 1L;
    MailSendCertificationCodeToEmailReq req = MailSendCertificationCodeToEmailReq.builder().email("test@naver.com")
        .build();
    //given
    given(userService.getUserWithDeletedByEmail(req.getEmail())).willReturn(user);
    //when
    mailService.sendCertificationCodeToEmail(req);
    //then
    then(userService).should(times(1)).getUserWithDeletedByEmail(user.getEmail());
    then(mailCommonService).should(times(1)).sendCode(user.getEmail(), user.getName());
  }

  @Test
  void sendCertificationCodeToEmailWithUserId() {
    Long userId = 1L;
    //given
    given(userService.getUserById(userId)).willReturn(user);
    //when
    mailService.sendCertificationCodeToEmail(userId);
    //then
    then(userService).should(times(1)).getUserById(userId);
    then(mailCommonService).should(times(1)).sendCode(user.getEmail(), user.getName());
  }
}
