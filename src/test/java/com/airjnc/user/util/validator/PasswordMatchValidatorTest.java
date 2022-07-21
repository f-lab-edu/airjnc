package com.airjnc.user.util.validator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import com.airjnc.common.util.BCryptHashEncrypter;
import com.airjnc.user.dto.PasswordMatchDTO;
import com.airjnc.user.exception.PasswordIsNotMatchException;
import com.testutil.annotation.UnitTest;
import com.testutil.fixture.PasswordMatchDTOFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@UnitTest
class PasswordMatchValidatorTest {

  PasswordMatchValidator passwordMatchValidator;


  @BeforeEach
  void beforeEach() {
    passwordMatchValidator = new PasswordMatchValidator();
  }

  @Test
  void whenPasswordIsMatchThenWillDoNothing() {
    //given
    PasswordMatchDTO passwordMatchDTO = PasswordMatchDTOFixture.getBuilder().build();
    //when
    passwordMatchValidator.validate(passwordMatchDTO);
    //then
  }

  @Test
  void whenPasswordIsNotMatchThenThrowPasswordIsNotMatchException() {
    //given
    PasswordMatchDTO passwordMatchDTO =
        PasswordMatchDTOFixture.getBuilder().password("123")
            .hash(BCryptHashEncrypter.encrypt("12345")).build();
    //when
    assertThrows(PasswordIsNotMatchException.class,
        () -> passwordMatchValidator.validate(passwordMatchDTO));
  }
}
