package com.airjnc.common.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties("cloud.naver.credential")
public class NcpCredentialProperties {

  private final String accessKey;

  private final String secretKey;
}
