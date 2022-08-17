package com.airjnc.common.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.airjnc.common.properties.NcpCredentialProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testutil.annotation.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@ExtendWith(MockitoExtension.class)
@UnitTest
class CommonNcpServiceTest {

  private final String uri = "/uri";

  @Mock
  ObjectMapper objectMapper;

  @Mock
  NcpCredentialProperties ncpCredentialProperties;

  @InjectMocks
  CommonNcpService commonNcpService;

  String accessKey = "accessKey";

  String secretKey = "secretKey";

  void assertHeaders(HttpHeaders headers) {
    // ncp api 요청시, 공용 Headers 값 검증
    assertThat(headers.getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    assertThat(headers.get("x-ncp-apigw-timestamp").get(0)).asString();
    assertThat(headers.get("x-ncp-iam-access-key").get(0)).isEqualTo(accessKey);
    assertThat(headers.get("x-ncp-apigw-signature-v2").get(0)).asString();
  }

  @Test
  void createHeaders() {
    //given
    given(ncpCredentialProperties.getAccessKey()).willReturn(accessKey);
    given(ncpCredentialProperties.getSecretKey()).willReturn(secretKey);
    //when
    HttpHeaders headers = commonNcpService.createHeaders(uri);
    //then
    assertHeaders(headers);
  }
}
