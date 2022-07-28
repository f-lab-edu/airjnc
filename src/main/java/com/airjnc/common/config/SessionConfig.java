package com.airjnc.common.config;

import com.airjnc.common.properties.SessionProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
@EnableConfigurationProperties(SessionProperties.class)
@RequiredArgsConstructor
public class SessionConfig {

  private final SessionProperties sessionProperties;

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();

    configuration.setHostName(sessionProperties.getHost());
    configuration.setPort(sessionProperties.getPort());
    configuration.setPassword(sessionProperties.getPassword());

    return new LettuceConnectionFactory(configuration);
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate(
      RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    // 아직까진 value 를 객체 등등.. 문자열 그 외의 타입으로 사용할 예정이 없으므로 String으로만 serialize진행
    // 추후에, value가 복잡해진다면 `GenericJackson2JsonRedisSerializer` 을 사용할 예정
    redisTemplate.setValueSerializer(new StringRedisSerializer());

    return redisTemplate;
  }
}
