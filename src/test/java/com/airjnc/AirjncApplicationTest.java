package com.airjnc;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("local")
@SpringBootTest
@Tag("integration")
public class AirjncApplicationTest {

  @Test
  void contextLoads() {
    // 지우지마세요. SpringBoot 의존성 테스트를 하기 위한 메소드입니다.
  }
}
