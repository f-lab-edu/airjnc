package com.airjnc.ncp.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "cloud.naver.credentials")
public class NcpCredentialsProperties {

  private final String accessKey;

  private final String secretKey;
}
