package com.airjnc.common.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testutil.annotation.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

@ExtendWith(MockitoExtension.class)
@UnitTest
class CommonUtilServiceTest {

  @Mock
  ObjectMapper objectMapper;

  @InjectMocks
  CommonUtilService commonUtilService;

  @Test
  void createEntity() throws JsonProcessingException {
    //given
    Object body = new Object();
    String jsonBody = "jsonBody";
    HttpHeaders headers = new HttpHeaders();
    given(objectMapper.writeValueAsString(body)).willReturn(jsonBody);
    //when
    HttpEntity<String> entity = commonUtilService.createHttpEntity(headers, body);
    //then
    then(objectMapper).should(times(1)).writeValueAsString(body);
    assertThat(entity.getBody()).isEqualTo(jsonBody);
  }

  @Test
  void generateCode() {
    //when
    String code = commonUtilService.generateCode();
    //then
    assertThat(code.length()).isEqualTo(6);
  }
}
