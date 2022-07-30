package com.airjnc.common.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import com.airjnc.common.exception.DefaultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommonCheckServiceTest {

  CommonCheckService commonCheckService;

  @BeforeEach
  void beforeEach() {
    commonCheckService = new CommonCheckService();
  }

  @Test
  void whenValuesOfActualAndExpectedAreDifferentThenThrowException() {
    assertThrows(
        DefaultException.class,
        () -> commonCheckService.shouldBeMatch(1, 2)
    );
  }

  @Test
  void whenValuesOfActualAndExpectedEqualThenWillDoNothing() {
    commonCheckService.shouldBeMatch(1, 1);
  }
}
