package com.airjnc.mail.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class NcpMailProperties {
  // naver cloud platform 에서만 사용하는 환경 변수들

  @Getter
  @Component
  public static class TemplateSid {

    @Value("${cloud.naver.mail.templateSid.resetPassword}")
    private int resetPassword;
  }

  @Getter
  @Component
  public static class Uri {

    @Value("${cloud.naver.mail.uri.send}")
    private String send;
  }
}
