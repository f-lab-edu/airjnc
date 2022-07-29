package com.airjnc;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

// test profile이 아닌 local profile로 진행합니다.
// 최소한의 환경[local]에서의 의존성이 정상적으로 작동되어야 합니다.
@ActiveProfiles("local")
@SpringBootTest
@Tag("integration")
public class AirjncApplicationTest {

  @Test
  void contextLoads() {
    // 지우지마세요. SpringBoot 의존성 테스트를 하기 위한 메소드입니다.
  }
}
