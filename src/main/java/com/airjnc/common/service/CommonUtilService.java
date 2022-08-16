package com.airjnc.common.service;

import com.airjnc.common.dao.RedisDao;
import com.airjnc.common.exception.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonUtilService {

  private final RedisDao redisDao;

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

  public String generateCode() {
    Random random = new Random(System.nanoTime());
    String code = null;
    while (true) {
      try {
        int n = random.nextInt(999_999);
        code = String.format("%06d", n);
        redisDao.get(code);
      } catch (NotFoundException e) {
        break;
      }
    }
    return code;
  }
}
