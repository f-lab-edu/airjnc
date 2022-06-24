package com.airjnc.user.service;

import com.airjnc.user.util.SessionKey;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.http.HttpSession;

import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;

@ExtendWith(MockitoExtension.class)
class SessionAuthServiceTest {
    @Mock
    HttpSession httpSession;
    @InjectMocks
    SessionAuthService sessionAuthService;


    @Test
    void sessionShouldBeSaved() {
        //when
        int expire = 1_800;
        SessionKey key = SessionKey.USER;
        Long userId = 1L;
        ReflectionTestUtils.setField(sessionAuthService, "expire", expire);
        //then
        sessionAuthService.logIn(key, userId);
        //then
        then(httpSession).should(times(1)).setMaxInactiveInterval(expire);
        then(httpSession).should(times(1)).setAttribute(key.name(), userId);
    }

    @Test
    void sessionShouldBeRemoved() {
        //when
        SessionKey key = SessionKey.USER;
        //then
        sessionAuthService.logOut(key);
        //then
        then(httpSession).should(times(1)).removeAttribute(key.name());
    }
}
