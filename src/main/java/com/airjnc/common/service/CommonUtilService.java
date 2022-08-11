package com.airjnc.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonUtilService {

  private final ObjectMapper objectMapper;

  public HttpEntity<String> createHttpEntity(HttpHeaders headers, Object body) {
    String jsonBody = null;
    try {
      jsonBody = objectMapper.writeValueAsString(body);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    return new HttpEntity<>(jsonBody, headers);
  }
}
