package com.airjnc.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import com.airjnc.common.exception.DefaultException;
import com.airjnc.common.exception.NotFoundException;
import com.airjnc.common.service.HashService;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.exception.EmailIsDuplicatedException;
import com.airjnc.user.exception.PasswordIsNotMatchException;
import com.testutil.testdata.TestUser;
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
class UserCheckServiceTest {

  @Mock
  UserRepository userRepository;

  @Spy
  HashService hashService;

  @InjectMocks
  UserCheckService userCheckService;


  private void assertObjectNameOfGlobalError(DefaultException e, String objectName) {
    Errors errors = e.getErrors();
    assertThat(Objects.requireNonNull(errors.getGlobalError()).getObjectName()).isEqualTo(objectName);
  }

  @Nested
  class EmailShouldNotBeDuplicated {

    @Test
    void whenEmailNotFoundThenSuccess() {
      //given
      String email = TestUser.EMAIL;
      given(userRepository.findByEmail(email)).willThrow(NotFoundException.class);
      //when
      userCheckService.emailShouldNotBeDuplicated(email);
    }

    @Test
    void whenEmailFoundAndDuplicatedThenThrow() {
      //given
      String email = TestUser.EMAIL;
      //when
      try {
        userCheckService.emailShouldNotBeDuplicated(email);
      } catch (EmailIsDuplicatedException e) {
        //then
        assertObjectNameOfGlobalError(e, "emailIsDuplicated");
      }
    }
  }

  @Nested
  class PasswordShouldBeMatch {

    @Test
    void whenPasswordIsMatchThenSuccess() {
      String plain = "plain";
      String hash = hashService.encrypt(plain);
      //when
      userCheckService.passwordShouldBeMatch(plain, hash);
    }

    @Test
    void whenPasswordIsNotMatchThenThrow() {
      String plain = "plain";
      String hash = hashService.encrypt("plain2");
      try {
        //when
        userCheckService.passwordShouldBeMatch(plain, hash);
      } catch (PasswordIsNotMatchException e) {
        //then
        assertObjectNameOfGlobalError(e, "passwordIsNotMatch");
      }
    }
  }
}
