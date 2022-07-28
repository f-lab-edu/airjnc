package com.airjnc.ncp.config;

import com.airjnc.ncp.annotation.NcpMailRestTemplate;
import com.airjnc.ncp.properties.NcpCredentialsProperties;
import com.airjnc.ncp.properties.NcpMailerProperties;
import com.airjnc.ncp.util.NcpMailerUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({NcpCredentialsProperties.class, NcpMailerProperties.class})
public class NcpConfig {

  private final RestTemplateBuilder restTemplateBuilder;

  @Bean
  @NcpMailRestTemplate
  public RestTemplate ncpMailRestTemplate() {
    return restTemplateBuilder.rootUri(NcpMailerUrl.BASE).build();
  }
}
