package com.airjnc.common.service;

import static org.assertj.core.api.Assertions.assertThat;
import com.testutil.annotation.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@UnitTest
class HashServiceTest {

  HashService hashService;

  @BeforeEach
  void beforeEach() {
    hashService = new HashService();
  }

  @Test
  void plainShouldBeHashed() {
    //given
    String plain = "q1w2e3r4!";
    //when
    String hash = hashService.encrypt(plain);
    //then
    assertThat(plain).isNotEqualTo(hash);
  }

  @Test
  void plainShouldBeMatchToB() {
    //given
    String plain = "q1w2e3r4!";
    String hash = hashService.encrypt(plain);
    //when
    boolean match = hashService.isMatch(plain, hash);
    //then
    assertThat(match).isTrue();
  }
}
