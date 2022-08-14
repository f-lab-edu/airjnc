package com.airjnc.common.dao;

import com.airjnc.common.exception.NotFoundException;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommonRedisDao {

  private final RedisTemplate<String, String> redisTemplate;

  public Boolean delete(String key) {
    return redisTemplate.delete(key);
  }

  public String get(String key) {
    String value = redisTemplate.opsForValue().get(key);
    if (value == null) {
      throw new NotFoundException(this.getClass().getSimpleName(), new Object[]{key});
    }
    return value;
  }

  public void store(String key, String value, Duration timeout) {
    redisTemplate.opsForValue().set(
        key,
        value,
        timeout
    );
  }
}
