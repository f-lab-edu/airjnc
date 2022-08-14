package com.airjnc.mail.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import com.airjnc.mail.dto.request.MailSendCertificationCodeToEmailReq;
import com.airjnc.user.dto.UserWhereDto;
import com.airjnc.user.dto.UserWhereDto.UserStatus;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.service.AssembleUserService;
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
class AssembleMailServiceTest {

  @Mock
  AssembleUserService assembleUserService;

  @Mock
  MailCommonService mailCommonService;

  @InjectMocks
  AssembleMailService assembleMailService;

  UserResp user;

  @BeforeEach
  void beforeEach() {
    user = UserRespFixture.getBuilder().build();
  }

  @Test
  void sendCertificationCodeToEmailWithUserId() {
    Long userId = 1L;
    //given
    given(assembleUserService.getUserById(userId, UserStatus.ACTIVE)).willReturn(user);
    //when
    assembleMailService.sendCertificationCodeToEmail(userId);
    //then
    then(assembleUserService).should(times(1)).getUserById(userId, UserStatus.ACTIVE);
    then(mailCommonService).should(times(1)).sendCode(user.getEmail(), user.getName());
  }

  @Test
  void sendCertificationCodeToEmail_noUserId() {
    MailSendCertificationCodeToEmailReq req = MailSendCertificationCodeToEmailReq.builder()
        .email("test@naver.com").build();
    //given
    given(assembleUserService.getUserByWhere(any(UserWhereDto.class))).willReturn(user);
    //when
    assembleMailService.sendCertificationCodeToEmail(req);
    //then
    then(assembleUserService).should(times(1)).getUserByWhere(any(UserWhereDto.class));
    then(mailCommonService).should(times(1)).sendCode(user.getEmail(), user.getName());
  }
}
