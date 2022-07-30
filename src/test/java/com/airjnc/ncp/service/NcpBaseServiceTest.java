package com.airjnc.ncp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import com.airjnc.ncp.properties.NcpCredentialsProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testutil.annotation.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@ExtendWith(MockitoExtension.class)
@UnitTest
class NcpBaseServiceTest {

  private final String url = "/url";

  @Mock
  NcpCredentialsProperties ncpCredentialsProperties;

  @Mock
  ObjectMapper objectMapper;

  @InjectMocks
  NcpBaseService ncpBaseService;

  void assertHeaders(HttpHeaders headers) {
    // ncp api 요청시, 공용 Headers 값 검증
    assertThat(headers.getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    assertThat(headers.get("x-ncp-apigw-timestamp").get(0)).asString();
    assertThat(headers.get("x-ncp-iam-access-key").get(0)).isEqualTo(
        ncpCredentialsProperties.getAccessKey());
    assertThat(headers.get("x-ncp-apigw-signature-v2").get(0)).asString();
  }

  @BeforeEach
  void beforeEach() {
    given(ncpCredentialsProperties.getAccessKey()).willReturn("accessKey");
    given(ncpCredentialsProperties.getSecretKey()).willReturn("secretKey");
  }

  @Test
  void createEntity() throws JsonProcessingException {
    //given
    Object body = new Object();
    String jsonBody = "jsonBody";
    given(objectMapper.writeValueAsString(body)).willReturn(jsonBody);
    //when
    HttpEntity<String> entity = ncpBaseService.createEntity(url, body);
    //then
    then(objectMapper).should(times(1)).writeValueAsString(body);
    assertHeaders(entity.getHeaders());
    assertThat(entity.getBody()).isEqualTo(jsonBody);
  }

  @Test
  void createHeaders() {
    //when
    HttpHeaders headers = ncpBaseService.createHeaders(url);
    //then
    assertHeaders(headers);
  }
}
