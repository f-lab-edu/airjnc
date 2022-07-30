package com.airjnc.ncp.service;

import com.airjnc.common.service.CommonCheckService;
import com.airjnc.ncp.annotation.NcpMailRestTemplate;
import com.airjnc.ncp.dto.NcpMailerReq;
import com.airjnc.ncp.dto.NcpMailerReq.Recipient;
import com.airjnc.ncp.dto.NcpMailerReq.Recipient.Type;
import com.airjnc.ncp.dto.NcpMailerResp;
import com.airjnc.ncp.dto.NcpMailerSendDto;
import com.airjnc.ncp.properties.NcpMailerProperties;
import com.airjnc.ncp.util.NcpApiUrl;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class NcpMailerService {

  @NcpMailRestTemplate
  private final RestTemplate restTemplate;

  private final NcpBaseService ncpBaseService;

  private final NcpMailerProperties ncpMailerProperties;

  private final CommonCheckService commonCheckService;

  public void send(NcpMailerSendDto ncpMailerSendDTO) {
    Recipient recipient = Recipient.builder()
        .address(ncpMailerSendDTO.getEmail())
        .type(Type.R)
        .parameters(Map.ofEntries(
            Map.entry("name", ncpMailerSendDTO.getName()),
            Map.entry("code", ncpMailerSendDTO.getCode())
        ))
        .build();
    NcpMailerReq body = NcpMailerReq.builder()
        .templateSid(ncpMailerProperties.getResetPasswordTemplateSid())
        .recipients(List.of(recipient))
        .build();
    HttpEntity<String> entity = ncpBaseService.createEntity(
        NcpApiUrl.Mailer.CREATE_MAIL_REQULEST,
        body
    );

    NcpMailerResp res = restTemplate.postForObject(
        NcpApiUrl.Mailer.CREATE_MAIL_REQULEST,
        entity,
        NcpMailerResp.class
    );
    commonCheckService.shouldBeMatch(Objects.requireNonNull(res).getCount(), 1);
  }
}
