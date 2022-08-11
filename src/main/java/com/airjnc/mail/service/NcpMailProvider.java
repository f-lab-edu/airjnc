package com.airjnc.mail.service;

import com.airjnc.common.service.CommonCheckService;
import com.airjnc.common.service.CommonNcpService;
import com.airjnc.common.service.CommonUtilService;
import com.airjnc.mail.annotation.NcpMailRestTemplate;
import com.airjnc.mail.dto.NcpMailSendReqDto;
import com.airjnc.mail.dto.NcpMailSendReqDto.Recipient;
import com.airjnc.mail.dto.NcpMailSendReqDto.Recipient.Type;
import com.airjnc.mail.dto.NcpMailSendRespDto;
import com.airjnc.mail.dto.SendUsingTemplateDto;
import com.airjnc.mail.properties.NcpMailProperties;
import com.google.common.annotations.VisibleForTesting;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// 특정 cloud에 종속
@Service
@RequiredArgsConstructor
public class NcpMailProvider implements MailProvider {

  @NcpMailRestTemplate
  private final RestTemplate restTemplate;

  private final CommonNcpService commonNcpService;

  private final CommonCheckService commonCheckService;

  private final CommonUtilService commonUtilService;

  private final NcpMailProperties.TemplateSid templateSidProperties;

  private final NcpMailProperties.Uri uriProperties;

  @VisibleForTesting
  public NcpMailSendReqDto createSendApiBody(SendUsingTemplateDto dto) {
    Recipient recipient = Recipient.builder()
        .type(Type.R)
        .parameters(Map.ofEntries(
            Map.entry("name", dto.getName()),
            Map.entry("certificationCode", dto.getCertificationCode())
        ))
        .build();
    return NcpMailSendReqDto.builder()
        .templateSid(templateSidProperties.getResetPassword())
        .recipients(List.of(recipient))
        .build();
  }

  @Override
  public void send(String email, SendUsingTemplateDto sendUsingTemplateDto) {
    NcpMailSendReqDto body = createSendApiBody(sendUsingTemplateDto);
    HttpHeaders headers = commonNcpService.createHeaders(uriProperties.getSend());
    HttpEntity<String> entity = commonUtilService.createHttpEntity(
        headers, body
    );
    NcpMailSendRespDto res = restTemplate.postForObject(
        uriProperties.getSend(),
        entity,
        NcpMailSendRespDto.class
    );

    commonCheckService.shouldBeMatch(Objects.requireNonNull(res).getCount(), 1);
  }
}
