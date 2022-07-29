package com.airjnc.common.aspect;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import com.airjnc.common.exception.UnauthorizedException;
import com.airjnc.user.service.UserStateService;
import com.testutil.annotation.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@UnitTest
class AdviceTest {

  @Mock
  UserStateService userStateService;

  @InjectMocks
  Advice advice;

  @Test
  void whenUserIdIsNullThenThrowUnauthorizedException() {
    //given
    given(userStateService.getUserId()).willReturn(null);
    //when & then
    assertThrows(UnauthorizedException.class, () -> advice.beforeCheckAuth());
  }

  @Test
  void whenUserItIsNotNullWillDoNothing() {
    //given
    Long userId = 1L;
    given(userStateService.getUserId()).willReturn(userId);
    //when
    advice.beforeCheckAuth();
  }
}
