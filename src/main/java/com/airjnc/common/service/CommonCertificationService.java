package com.airjnc.common.service;

import com.airjnc.common.dao.RedisDao;
import com.airjnc.common.exception.NotFoundException;
import com.airjnc.common.properties.SessionTtlProperties;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonCertificationService {

  private final RedisDao redisDao;

  private final SessionTtlProperties sessionTtlProperties;

  private String generateCode() {
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

  public String getCodeAndStoreToRedis(String key) {
    String code = generateCode();
    redisDao.store(code, key, sessionTtlProperties.getCertificationCode());
    return code;
  }
}
