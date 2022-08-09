package com.airjnc.user.util.validator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import com.airjnc.common.exception.NotFoundException;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.CreateDTO;
import com.airjnc.user.exception.EmailIsDuplicatedException;
import com.airjnc.user.service.UserCheckService;
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
  UserCheckService userCheckService;

  CreateDTO createDTO;

  @BeforeEach
  void beforeEach() {
    createDTO = CreateDTOFixture.getBuilder().build();
  }

  @Test
  void whenEmailOfSignUpDTOisDuplicatedThenExceptionIsThrown() {
    // given
    given(userRepository.findByEmail(createDTO.getEmail())).willReturn(
        UserEntity.builder().build());
    //when
    assertThrows(
        EmailIsDuplicatedException.class,
        () -> userCheckService.emailShouldNotBeDuplicated(createDTO.getEmail())
    );
    //then
    then(userRepository).should(times(1)).findByEmail(createDTO.getEmail());
  }

  @Test
  void whenEmailOfSignUpDTOisEmptyThenExceptionIsNotThrown() {
    // given
    given(userRepository.findByEmail(createDTO.getEmail())).willThrow(NotFoundException.class);
    //when
    userCheckService.emailShouldNotBeDuplicated(createDTO.getEmail());
    //then
    then(userRepository).should(times(1)).findByEmail(createDTO.getEmail());
  }
}
