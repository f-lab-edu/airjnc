package com.airjnc.common.service;

import com.airjnc.common.dao.RedisDao;
import com.airjnc.common.exception.DefaultException;
import com.testutil.annotation.UnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@UnitTest
@ExtendWith(MockitoExtension.class)
class CommonValidateServiceTest {

  @Mock
  RedisDao redisDao;

  @InjectMocks
  CommonValidateService commonValidateService;

  @Test
  void verifyCertificationCode() {
    // given
    String key = "key";
    String code = "code";
    String codeFromRedis = "code";
    given(redisDao.get(key)).willReturn(codeFromRedis);
    //when
    commonValidateService.verifyCertificationCode(key, code);
    //then
    then(redisDao).should().get(key);
    then(redisDao).should().delete(key);
  }

  @Test
  @DisplayName("actual과 expected가 동일할 경우, 아무 일이 생기면 안된다.")
  void whenEqualThenNothing() {
    commonValidateService.shouldBeMatch(1, 1);
  }

  @Test
  @DisplayName("actual과 expected가 동일하지 않을 경우, DefaultException이 발생해야 한다.")
  void whenEqualThenThrowException() {
    assertThrows(
            DefaultException.class,
            () -> commonValidateService.shouldBeMatch(1, 2)
    );
  }
}
