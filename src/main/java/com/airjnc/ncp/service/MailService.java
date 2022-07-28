package com.airjnc.ncp.service;

import com.airjnc.common.service.CommonInternalCheckService;
import com.airjnc.ncp.annotation.NcpMailRestTemplate;
import com.airjnc.ncp.dto.MailSendDTO;
import com.airjnc.ncp.dto.NcpMailerRequest;
import com.airjnc.ncp.dto.NcpMailerRequest.Recipient;
import com.airjnc.ncp.dto.NcpMailerRequest.Recipient.Type;
import com.airjnc.ncp.dto.NcpMailerResponse;
import com.airjnc.ncp.properties.NcpMailerProperties;
import com.airjnc.ncp.util.NcpMailerUrl;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MailService {

  @NcpMailRestTemplate
  private final RestTemplate restTemplate;

  private final NcpCommonService ncpCommonService;

  private final NcpMailerProperties ncpMailerProperties;

  private final CommonInternalCheckService commonInternalCheckService;

  public void send(MailSendDTO mailSendDTO) {

    Recipient recipient = Recipient.builder()
        .address(mailSendDTO.getEmail())
        .type(Type.R)
        .parameters(Map.ofEntries(
            Map.entry("name", mailSendDTO.getName()),
            Map.entry("code", mailSendDTO.getCode())
        ))
        .build();
    NcpMailerRequest body = NcpMailerRequest.builder()
        .templateSid(ncpMailerProperties.getResetPasswordTemplateSid())
        .recipients(List.of(recipient))
        .build();
    HttpEntity<String> entity = ncpCommonService.createEntity(
        NcpMailerUrl.CREATE_MAIL_REQULEST,
        body
    );

    NcpMailerResponse res = restTemplate.postForObject(
        NcpMailerUrl.CREATE_MAIL_REQULEST,
        entity,
        NcpMailerResponse.class
    );
    commonInternalCheckService.shouldBeMatch(Objects.requireNonNull(res).getCount(), 1);
  }
}
