package com.airjnc.mail.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.airjnc.common.interceptor.CheckAuthInterceptor;
import com.airjnc.common.resolver.CurrentUserIdArgumentResolver;
import com.airjnc.common.service.StateService;
import com.airjnc.mail.dto.request.MailSendCertificationCodeToEmailReq;
import com.airjnc.mail.service.AssembleMailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testutil.annotation.IntegrationTest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MailController.class)
@IntegrationTest
class MailControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  AssembleMailService assembleMailService;

  @SpyBean
  CheckAuthInterceptor checkAuthInterceptor;

  @SpyBean
  CurrentUserIdArgumentResolver currentUserIdArgumentResolver;

  @MockBean
  StateService stateService;

  private void checkInterceptorAndArgumentResolver() throws Exception {
    then(checkAuthInterceptor).should(times(1))
        .preHandle(any(HttpServletRequest.class), any(HttpServletResponse.class), any(Object.class));
    then(currentUserIdArgumentResolver).should(times(1))
        .resolveArgument(any(), any(), any(), any());
  }

  @Test
  void sendCertificationCodeToEmail_isAuth() throws Exception {
    //given
    Long userId = 1L;
    given(stateService.getUserId()).willReturn(userId);
    //when
    mockMvc.perform(
            post("/mail/certificationCode")
                .param("to", "email")
                .param("isAuth", "1")
        ).andDo(print())
        .andExpect(status().isOk());
    //then
    checkInterceptorAndArgumentResolver();
    then(assembleMailService).should().sendCertificationCodeToEmail(userId);
  }

  @Test
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
    then(assembleMailService).should().sendCertificationCodeToEmail(any(MailSendCertificationCodeToEmailReq.class));
  }
}
