package com.airjnc.user.service;

import com.airjnc.common.exception.DefaultException;
import com.airjnc.common.service.CommonHashService;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.dto.UserWhereDto;
import com.airjnc.user.exception.EmailIsDuplicatedException;
import com.airjnc.user.exception.PasswordIsNotMatchException;
import com.testutil.annotation.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.Errors;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@UnitTest
class UserValidateServiceTest {

  @Mock
  UserRepository userRepository;

  @Spy
  CommonHashService commonHashService;

  @InjectMocks
  UserValidateService userValidateService;


  private void assertObjectNameOfGlobalError(DefaultException e, String objectName) {
    Errors errors = e.getErrors();
    assertThat(Objects.requireNonNull(errors.getGlobalError()).getObjectName()).isEqualTo(objectName);
  }

  @Test
  @DisplayName("중복된 이메일이라면, EmailIsDuplicatedException을 던져야 한다.")
  void whenEmailIsDuplicatedThenThrow() {
    //given
    given(userRepository.exists(any(UserWhereDto.class))).willReturn(true);
    //when
    try {
      userValidateService.emailShouldNotBeDuplicated("test@naver.com");
    } catch (EmailIsDuplicatedException e) {
      //then
      assertObjectNameOfGlobalError(e, "emailIsDuplicated");
    }
  }

  @Test
  @DisplayName("중복된 이메일이 아니라면, 아무 일이 생기면 안된다.")
  void whenEmailIsNotFoundThenSuccess() {
    //given
    given(userRepository.exists(any(UserWhereDto.class))).willReturn(false);
    //when
    userValidateService.emailShouldNotBeDuplicated("test@naver.com");
  }


  @Test
  @DisplayName("비밀번호가 일치하면, 아무 일이 생기면 안된다.")
  void whenPasswordIsMatchThenSuccess() {
    String plain = "plain";
    String hash = commonHashService.encrypt(plain);
    //when
    userValidateService.plainAndHashShouldMatch(plain, hash);
  }

  @Test
  @DisplayName("비밀번호가 일치하지 않다면, PasswordIsNotMatchException을 던져야 한다.")
  void whenPasswordIsNotMatchThenThrow() {
    String plain = "plain";
    String hash = commonHashService.encrypt("plain2");
    try {
      //when
      userValidateService.plainAndHashShouldMatch(plain, hash);
    } catch (PasswordIsNotMatchException e) {
      //then
      assertObjectNameOfGlobalError(e, "passwordIsNotMatch");
    }
  }
}
