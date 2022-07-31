package com.airjnc.common.service;

import static org.assertj.core.api.Assertions.assertThat;
import com.airjnc.common.dao.RedisDao;
import com.testutil.annotation.RedisTest;
import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

@RedisTest
class RedisDaoTest {

  @Autowired
  RedisTemplate<String, String> redisTemplate;

  RedisDao redisDao;

  @BeforeEach
  void beforeEach() {
    // redisService는 @Bean을 통해 bean으로 생성해주지 않았기 때문에, 직접 만들어준다.
    redisDao = new RedisDao(redisTemplate);
  }

  @Test
  void whenStoreThenGetValueOfTheKey() {
    //given
    String key = "key";
    String value = "value";
    Duration timeout = Duration.ofMinutes(1L);
    //when
    redisDao.store(key, value, timeout);
    //then
    String result = redisDao.get(key);
    assertThat(result).isEqualTo(value);

    // temp
    // docker test로 변경하면 지울 것
    redisTemplate.delete(key);
  }
}
