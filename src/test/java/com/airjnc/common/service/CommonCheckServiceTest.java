package com.airjnc.common.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import com.airjnc.common.dao.RedisDao;
import com.airjnc.common.exception.DefaultException;
import com.testutil.annotation.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@UnitTest
@ExtendWith(MockitoExtension.class)
class CommonCheckServiceTest {

  @Mock
  RedisDao redisDao;

  @InjectMocks
  CommonCheckService commonCheckService;

  @Test
  void verifyCode() {
    // given
    String key = "key";
    String code = "code";
    String codeFromRedis = "code";
    given(redisDao.get(key)).willReturn(codeFromRedis);
    //when
    commonCheckService.verifyCertificationCode(key, code);
    //then
    then(redisDao).should(times(1)).get(key);
    then(redisDao).should(times(1)).delete(key);
  }

  @Test
  void whenEqualThenNothing() {
    commonCheckService.shouldBeMatch(1, 1);
  }

  @Test
  void whenEqualThenThrowException() {
    assertThrows(
        DefaultException.class,
        () -> commonCheckService.shouldBeMatch(1, 2)
    );
  }
}
