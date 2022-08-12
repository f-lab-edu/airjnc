package com.airjnc.mail.config;

import com.airjnc.mail.annotation.NcpMailRestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class MailConfig {

  // 사용할 cloud에 대한 restTemplate 설정
  private final RestTemplateBuilder restTemplateBuilder;

  @Value("${cloud.naver.mail.uri.root}")
  private final String rootUri;

  @Bean
  @NcpMailRestTemplate // 여러 cloud를 사용할 경우에, 이를 나누기 위해 Qualifier 사용
  public RestTemplate ncpMailRestTemplate() {
    return restTemplateBuilder.rootUri(rootUri).build();
  }
}
