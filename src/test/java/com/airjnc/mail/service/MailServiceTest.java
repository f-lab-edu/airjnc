package com.airjnc.mail.service;

import com.airjnc.mail.dto.request.MailSendCertificationCodeToEmailReq;
import com.airjnc.user.dto.UserWhereDto;
import com.airjnc.user.dto.UserWhereDto.UserStatus;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.service.UserService;
import com.testutil.annotation.UnitTest;
import com.testutil.fixture.user.UserRespFixture;
import com.testutil.testdata.TestUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

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
  @DisplayName("userId를 통해 이메일 보내기 검증")
  void sendCertificationCodeToEmail_UserId() {
    //given
    Long userId = TestUser.ID;
    given(userService.getUserById(userId, UserStatus.ACTIVE)).willReturn(user);
    //when
    mailService.sendCertificationCodeToEmail(userId);
    //then
    then(userService).should().getUserById(userId, UserStatus.ACTIVE);
    then(mailCommonService).should().sendCode(user.getEmail(), user.getName());
  }

  @Test
  @DisplayName("userId가 아닌 MailSendCertificationCodeToEmailReq를 통해 이메일 보내기 검증")
  void sendCertificationCodeToEmail_noUserId() {
    MailSendCertificationCodeToEmailReq req = MailSendCertificationCodeToEmailReq.builder()
            .email(TestUser.EMAIL).build();
    //given
    given(userService.getUserByWhere(any(UserWhereDto.class))).willReturn(user);
    //when
    mailService.sendCertificationCodeToEmail(req);
    //then
    then(userService).should().getUserByWhere(any(UserWhereDto.class));
    then(mailCommonService).should().sendCode(user.getEmail(), user.getName());
  }
}
