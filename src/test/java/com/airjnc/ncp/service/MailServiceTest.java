package com.airjnc.ncp.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import com.airjnc.common.service.CommonInternalCheckService;
import com.airjnc.ncp.dto.MailSendDTO;
import com.airjnc.ncp.dto.NcpMailerRequest;
import com.airjnc.ncp.dto.NcpMailerResponse;
import com.airjnc.ncp.properties.NcpMailerProperties;
import com.airjnc.ncp.util.NcpMailerUrl;
import com.testutil.annotation.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
@UnitTest
class MailServiceTest {

  @Mock
  RestTemplate restTemplate;

  @Mock
  NcpCommonService ncpCommonService;

  @Mock
  NcpMailerProperties ncpMailerProperties;

  @Mock
  CommonInternalCheckService commonInternalCheckService;

  @InjectMocks
  MailService mailService;

  @Test
  void send() {
    //given
    MailSendDTO mailSendDTO = MailSendDTO.builder()
        .email("hanjn2842@naver.com")
        .name("한재남")
        .code("12345")
        .build();

    HttpEntity<String> entity = new HttpEntity<>("body");
    given(ncpCommonService.createEntity(
        eq(NcpMailerUrl.CREATE_MAIL_REQULEST),
        any(NcpMailerRequest.class)
    )).willReturn(entity);

    NcpMailerResponse res = NcpMailerResponse.builder().requestId("requestId").count(1)
        .build();
    given(restTemplate.postForObject(
        NcpMailerUrl.CREATE_MAIL_REQULEST,
        entity,
        NcpMailerResponse.class
    )).willReturn(res);
    //when
    mailService.send(mailSendDTO);
    //then
    then(ncpCommonService)
        .should(times(1))
        .createEntity(eq(NcpMailerUrl.CREATE_MAIL_REQULEST), any(NcpMailerRequest.class));
    then(restTemplate)
        .should(times(1))
        .postForObject(
            NcpMailerUrl.CREATE_MAIL_REQULEST,
            entity,
            NcpMailerResponse.class
        );
    then(commonInternalCheckService)
        .should(times(1))
        .shouldBeMatch(res.getCount(), 1);
  }
}
