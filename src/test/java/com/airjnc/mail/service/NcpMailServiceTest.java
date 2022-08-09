package com.airjnc.mail.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import com.airjnc.common.service.CommonCheckService;
import com.airjnc.common.service.CommonUtilService;
import com.airjnc.common.service.CommonNcpService;
import com.airjnc.mail.dto.NcpMailSendReqDto;
import com.airjnc.mail.dto.NcpMailSendReqDto.Recipient;
import com.airjnc.mail.dto.NcpMailSendRespDto;
import com.airjnc.mail.dto.SendUsingTemplateDto;
import com.airjnc.mail.properties.NcpMailProperties;
import com.testutil.annotation.UnitTest;
import com.testutil.testdata.TestUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
@UnitTest
class NcpMailServiceTest {

  @Mock
  RestTemplate restTemplate;

  @Mock
  CommonUtilService commonUtilService;

  @Mock
  CommonNcpService commonNcpService;

  @Mock
  CommonCheckService commonCheckService;

  @Mock
  NcpMailProperties.Uri uri;

  @Mock
  NcpMailProperties.TemplateSid templateSid;

  @InjectMocks
  NcpMailService ncpMailService;


  @Test
  void createSendApiBody() {
    //given
    String name = "name";
    String code = "code";
    int resetPasswordTemplateSid = 123;
    SendUsingTemplateDto dto = SendUsingTemplateDto.builder()
        .name(name).code(code).build();
    given(templateSid.getResetPassword()).willReturn(resetPasswordTemplateSid);
    //when
    NcpMailSendReqDto result = ncpMailService.createSendApiBody(dto);
    //then
    assertThat(result.getTemplateSid()).isEqualTo(resetPasswordTemplateSid);
    Recipient recipients = result.getRecipients().get(0);
    assertThat(recipients.getParameters().get("name")).isEqualTo(name);
    assertThat(recipients.getParameters().get("code")).isEqualTo(code);

  }

  @Test
  void send() {
    //given
    String sendUri = "send";
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<String> entity = new HttpEntity<>("body");
    NcpMailSendRespDto res = NcpMailSendRespDto.builder().requestId("requestId").count(1).build();

    given(uri.getSend()).willReturn(sendUri);
    given(commonNcpService.createHeaders(sendUri)).willReturn(headers);
    given(commonUtilService.createHttpEntity(eq(headers), any(NcpMailSendReqDto.class))).willReturn(entity);
    given(restTemplate.postForObject(sendUri, entity, NcpMailSendRespDto.class)).willReturn(res);
    //when
    SendUsingTemplateDto sendUsingTemplateDto = SendUsingTemplateDto.builder().name(TestUser.NAME).code("123456")
        .build();
    ncpMailService.send(TestUser.EMAIL, sendUsingTemplateDto);
    //then
    then(commonNcpService).should(times(1)).createHeaders(sendUri);
    then(commonUtilService).should(times(1)).createHttpEntity(eq(headers), any(NcpMailSendReqDto.class));
    then(restTemplate).should(times(1)).postForObject(sendUri, entity, NcpMailSendRespDto.class);
    then(commonCheckService).should(times(1)).shouldBeMatch(res.getCount(), 1);
  }
}
