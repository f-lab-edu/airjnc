package com.airjnc.user.util.validator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import com.airjnc.common.util.BCryptHashEncrypter;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.exception.PasswordIsNotMatchException;
import com.airjnc.user.service.UserCheckService;
import com.testutil.annotation.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class PasswordMatchValidatorTest {

  @InjectMocks
  UserCheckService userCheckService;

  @Mock
  UserRepository userRepository;

  @Test
  void whenPasswordIsMatchThenWillDoNothing() {
    //given
    String plain = "plain";
    String hash = BCryptHashEncrypter.encrypt(plain);
    //when
    userCheckService.passwordShouldBeMatch(plain, hash);
  }

  @Test
  void whenPasswordIsNotMatchThenThrowPasswordIsNotMatchException() {
    //given
    String plain = "plain";
    String hash = BCryptHashEncrypter.encrypt("other");
    //when
    assertThrows(PasswordIsNotMatchException.class,
        () -> userCheckService.passwordShouldBeMatch(plain, hash));
  }
}
