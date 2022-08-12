package com.airjnc.mail.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import com.airjnc.mail.dto.request.MailSendCertificationCodeToEmailReq;
import com.airjnc.user.dto.UserWhereDto.UserStatus;
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
  void sendCertificationCodeToEmailWithUserId() {
    Long userId = 1L;
    //given
    given(userService.getUserById(userId, UserStatus.ACTIVE)).willReturn(user);
    //when
    mailService.sendCertificationCodeToEmail(userId);
    //then
    then(userService).should(times(1)).getUserById(userId, UserStatus.ACTIVE);
    then(mailCommonService).should(times(1)).sendCode(user.getEmail(), user.getName());
  }

  @Test
  void sendCertificationCodeToEmail_noUserId() {
    Long userId = 1L;
    MailSendCertificationCodeToEmailReq req = MailSendCertificationCodeToEmailReq.builder().email("test@naver.com")
        .build();
    //given
    given(userService.getUserByWhere(req.getEmail(), UserStatus.ALL)).willReturn(user);
    //when
    mailService.sendCertificationCodeToEmail(req);
    //then
    then(userService).should(times(1)).getUserByWhere(user.getEmail(), UserStatus.ALL);
    then(mailCommonService).should(times(1)).sendCode(user.getEmail(), user.getName());
  }
}
