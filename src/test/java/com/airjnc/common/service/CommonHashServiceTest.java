package com.airjnc.common.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.testutil.annotation.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@UnitTest
class CommonHashServiceTest {

  CommonHashService commonHashService;

  @BeforeEach
  void beforeEach() {
    commonHashService = new CommonHashService();
  }

  @Test
  void plainShouldBeHashed() {
    //given
    String plain = "q1w2e3r4!";
    //when
    String hash = commonHashService.encrypt(plain);
    //then
    assertThat(plain).isNotEqualTo(hash);
  }

  @Test
  void plainShouldBeMatchToB() {
    //given
    String plain = "q1w2e3r4!";
    String hash = commonHashService.encrypt(plain);
    //when
    boolean match = commonHashService.isMatch(plain, hash);
    //then
    assertThat(match).isTrue();
  }
}
