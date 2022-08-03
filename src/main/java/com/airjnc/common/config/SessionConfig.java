package com.airjnc.common.config;

import com.airjnc.common.properties.SessionTtlProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
@EnableConfigurationProperties(SessionTtlProperties.class)
public class SessionConfig {

  @Value("${spring.redis.session.host}")
  private String host;

  @Value("${spring.redis.session.port}")
  private int port;

  @Value("${spring.redis.session.password}")
  private String password;

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();

    configuration.setHostName(host);
    configuration.setPort(port);
    configuration.setPassword(password);

    return new LettuceConnectionFactory(configuration);
  }

  @Bean
  public RedisTemplate<String, String> redisTemplate(
      RedisConnectionFactory redisConnectionFactory) {
    // 아직까진 value 를 객체 등등.. 문자열 그 외의 타입으로 사용할 예정이 없으므로 String으로만 serialize 진행
    // 추후에, value가 복잡해진다면 `GenericJackson2JsonRedisSerializer` 을 사용할 예정
    StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
    stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
    return stringRedisTemplate;
  }
}
