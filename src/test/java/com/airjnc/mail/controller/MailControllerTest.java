package com.airjnc.mail.controller;

import com.airjnc.common.interceptor.CheckAuthInterceptor;
import com.airjnc.common.resolver.CurrentUserIdArgumentResolver;
import com.airjnc.common.service.StateService;
import com.airjnc.common.util.enumerate.SessionKey;
import com.airjnc.mail.dto.request.MailSendCertificationCodeToEmailReq;
import com.airjnc.mail.service.MailService;
import com.airjnc.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testutil.annotation.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MailController.class)
@IntegrationTest
class MailControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  MailService mailService;

  @SpyBean
  CheckAuthInterceptor checkAuthInterceptor;

  @SpyBean
  CurrentUserIdArgumentResolver currentUserIdArgumentResolver;

  @MockBean
  UserService userService;

  @MockBean
  StateService stateService;

  private void checkInterceptorAndArgumentResolver() throws Exception {
    then(checkAuthInterceptor).should()
            .preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class), any(Object.class));
    then(currentUserIdArgumentResolver).should()
            .resolveArgument(any(), any(), any(), any());
  }

  @Test
  @DisplayName("인증되있는 상태에서 인증 코드를 이메일로 보내기")
  void sendCertificationCodeToEmail_isAuth() throws Exception {
    //given
    Long userId = 1L;
    given(stateService.get(SessionKey.USER)).willReturn(userId);
    //when
    mockMvc.perform(
                    post("/mail/certificationCode")
                            .param("to", "email")
                            .param("isAuth", "1")
            ).andDo(print())
            .andExpect(status().isOk());
    //then
    checkInterceptorAndArgumentResolver();
    then(mailService).should().sendCertificationCodeToEmail(userId);
  }

  @Test
  @DisplayName("인증되지 않는 상태에서 인증 코드를 이메일로 보내기")
  void sendCertificationCodeToEmail_noAuth() throws Exception {
    //given
    MailSendCertificationCodeToEmailReq req = MailSendCertificationCodeToEmailReq.builder().email("test@naver.com")
            .build();
    //when
    mockMvc.perform(
                    post("/mail/certificationCode")
                            .param("to", "email")
                            .param("isAuth", "0")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req))
            ).andDo(print())
            .andExpect(status().isOk());
    //then
    then(mailService).should().sendCertificationCodeToEmail(any(MailSendCertificationCodeToEmailReq.class));
  }
}
