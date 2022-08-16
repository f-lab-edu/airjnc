package com.airjnc.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import com.airjnc.common.exception.DefaultException;
import com.airjnc.common.service.CommonHashService;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.dto.UserWhereDto;
import com.airjnc.user.exception.EmailIsDuplicatedException;
import com.airjnc.user.exception.PasswordIsNotMatchException;
import com.testutil.annotation.UnitTest;
import java.util.Objects;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.Errors;

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

  @Nested
  class EmailShouldNotBeDuplicated {

    @Test
    void whenEmailFoundAndDuplicatedThenThrow() {
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
    void whenEmailNotFoundThenSuccess() {
      //given
      given(userRepository.exists(any(UserWhereDto.class))).willReturn(false);
      //when
      userValidateService.emailShouldNotBeDuplicated("test@naver.com");
    }
  }

  @Nested
  class PasswordShouldBeMatch {

    @Test
    void whenPasswordIsMatchThenSuccess() {
      String plain = "plain";
      String hash = commonHashService.encrypt(plain);
      //when
      userValidateService.passwordShouldBeMatch(plain, hash);
    }

    @Test
    void whenPasswordIsNotMatchThenThrow() {
      String plain = "plain";
      String hash = commonHashService.encrypt("plain2");
      try {
        //when
        userValidateService.passwordShouldBeMatch(plain, hash);
      } catch (PasswordIsNotMatchException e) {
        //then
        assertObjectNameOfGlobalError(e, "passwordIsNotMatch");
      }
    }
  }
}
