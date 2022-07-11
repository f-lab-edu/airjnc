package com.airjnc.user.service;

import com.airjnc.common.util.constant.SessionKey;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpSession;

import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;

@ExtendWith(MockitoExtension.class)
class SessionServiceTest {
    @Mock
    HttpSession httpSession;
    @InjectMocks
    SessionService sessionService;


    @Test
    void sessionShouldBeSaved() {
        //when
        Long userId = 1L;
        //then
        sessionService.create(userId);
        //then
        then(httpSession).should(times(1)).setAttribute(SessionKey.USER.name(), userId);
    }

    @Test
    void sessionShouldBeRemoved() {
        //then
        sessionService.remove();
        //then
        then(httpSession).should(times(1)).removeAttribute(SessionKey.USER.name());
    }
}
