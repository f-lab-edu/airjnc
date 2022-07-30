package com.airjnc.ncp.service;

import com.airjnc.common.service.CommonCheckService;
import com.airjnc.ncp.annotation.NcpMailRestTemplate;
import com.airjnc.ncp.properties.NcpMailerProperties;
import com.airjnc.ncp.dto.NcpMailerReq;
import com.airjnc.ncp.dto.NcpMailerReq.Recipient;
import com.airjnc.ncp.dto.NcpMailerReq.Recipient.Type;
import com.airjnc.ncp.dto.NcpMailerRes;
import com.airjnc.ncp.dto.NcpMailerSendDTO;
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
public class NcpMailerService {

  @NcpMailRestTemplate
  private final RestTemplate restTemplate;

  private final NcpCommonService ncpCommonService;

  private final NcpMailerProperties ncpMailerProperties;

  private final CommonCheckService commonCheckService;

  public void send(NcpMailerSendDTO ncpMailerSendDTO) {
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
    HttpEntity<String> entity = ncpCommonService.createEntity(
        NcpMailerUrl.CREATE_MAIL_REQULEST,
        body
    );

    NcpMailerRes res = restTemplate.postForObject(
        NcpMailerUrl.CREATE_MAIL_REQULEST,
        entity,
        NcpMailerRes.class
    );
    commonCheckService.shouldBeMatch(Objects.requireNonNull(res).getCount(), 1);
  }
}
