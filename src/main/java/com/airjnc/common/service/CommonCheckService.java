package com.airjnc.common.service;

import com.airjnc.common.dao.CommonRedisDao;
import com.airjnc.common.exception.BadRequestException;
import com.airjnc.common.exception.DefaultException;
import com.airjnc.common.util.factory.ErrorsFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonCheckService {

  private final CommonRedisDao commonRedisDao;

  public void shouldBeMatch(int actual, int expected) {
    if (actual == expected) {
      return;
    }
    /*
    1. CommonCheckService.isNotMatch
    2. CommonCheckService
     */
    throw new DefaultException(
        ErrorsFactory.createAndReject(this.getClass().getSimpleName(), "shouldBeMatch", new Object[]{actual, expected})
    );
  }

  public void shouldBeMatch(String actual, String expected) {
    if (actual.equals(expected)) {
      return;
    }
    /*
    1. CommonCheckService.isNotMatch
    2. CommonCheckService
     */
    throw new BadRequestException(
        ErrorsFactory.createAndReject(this.getClass().getSimpleName(), "shouldBeMatch", new Object[]{actual, expected})
    );
  }

  public void verifyCode(String key, String code) {
    String codeFromRedis = commonRedisDao.get(key);
    shouldBeMatch(code, codeFromRedis);
    commonRedisDao.delete(key);
  }
}
