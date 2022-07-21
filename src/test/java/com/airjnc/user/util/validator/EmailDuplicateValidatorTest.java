package com.airjnc.user.util.validator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import com.airjnc.common.exception.NotFoundException;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.CreateDTO;
import com.airjnc.user.exception.DuplicatedEmailException;
import com.testutil.annotation.UnitTest;
import com.testutil.fixture.CreateDTOFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@UnitTest
class EmailDuplicateValidatorTest {

  @Mock
  UserRepository userRepository;

  @InjectMocks
  EmailDuplicateValidator emailDuplicateValidator;

  CreateDTO createDTO;

  @BeforeEach
  void beforeEach() {
    createDTO = CreateDTOFixture.getBuilder().build();
  }

  @Test
  void whenEmailOfSignUpDTOisEmptyThenExceptionIsNotThrown() {
    // given
    given(userRepository.findByEmail(createDTO.getEmail())).willThrow(NotFoundException.class);
    //when
    emailDuplicateValidator.validate(createDTO);
    //then
    then(userRepository).should(times(1)).findByEmail(createDTO.getEmail());
  }

  @Test
  void whenEmailOfSignUpDTOisDuplicatedThenExceptionIsThrown() {
    // given
    given(userRepository.findByEmail(createDTO.getEmail())).willReturn(
        UserEntity.builder().build());
    //when
    assertThrows(
        DuplicatedEmailException.class,
        () -> emailDuplicateValidator.validate(createDTO)
    );
    //then
    then(userRepository).should(times(1)).findByEmail(createDTO.getEmail());
  }
}
