package com.airjnc.mail.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.airjnc.common.service.CommonNcpService;
import com.airjnc.common.service.CommonUtilService;
import com.airjnc.common.service.CommonValidateService;
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
class NcpMailProviderTest {

  @Mock
  RestTemplate restTemplate;

  @Mock
  CommonUtilService commonUtilService;

  @Mock
  CommonNcpService commonNcpService;

  @Mock
  CommonValidateService commonValidateService;

  @Mock
  NcpMailProperties.Uri uri;

  @Mock
  NcpMailProperties.TemplateSid templateSid;

  @InjectMocks
  NcpMailProvider ncpMailService;


  @Test
  void createSendApiBody() {
    //given
    String name = TestUser.NAME;
    String code = "code";
    int resetPasswordTemplateSid = 123;
    SendUsingTemplateDto dto = SendUsingTemplateDto.builder()
        .name(name).certificationCode(code).build();
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
    SendUsingTemplateDto sendUsingTemplateDto = SendUsingTemplateDto.builder().name(TestUser.NAME)
        .certificationCode("123456")
        .build();
    ncpMailService.send(TestUser.EMAIL, sendUsingTemplateDto);
    //then
    then(commonNcpService).should().createHeaders(sendUri);
    then(commonUtilService).should().createHttpEntity(eq(headers), any(NcpMailSendReqDto.class));
    then(restTemplate).should().postForObject(sendUri, entity, NcpMailSendRespDto.class);
    then(commonValidateService).should().shouldBeMatch(res.getCount(), 1);
  }
}
