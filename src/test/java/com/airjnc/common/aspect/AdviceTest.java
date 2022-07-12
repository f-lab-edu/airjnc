package com.airjnc.common.aspect;

import com.airjnc.common.exception.UnauthorizedException;
import com.airjnc.user.service.StateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AdviceTest {
    @Mock
    StateService stateService;
    @InjectMocks
    Advice advice;

    @Test
    void whenUserIdIsNullThenThrowUnauthorizedException() {
        //given
        given(stateService.getUserId()).willReturn(null);
        //when & then
        assertThrows(UnauthorizedException.class, () -> advice.beforeCheckAuth());
    }

    @Test
    void whenUserItIsNotNullWillDoNothing() {
        //given
        Long userId = 1L;
        given(stateService.getUserId()).willReturn(userId);
        //when
        advice.beforeCheckAuth();
    }
}
