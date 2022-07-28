package com.airjnc.common.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import com.airjnc.common.exception.DefaultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommonInternalCheckServiceTest {

  CommonInternalCheckService commonInternalCheckService;

  @BeforeEach
  void beforeEach() {
    commonInternalCheckService = new CommonInternalCheckService();
  }

  @Test
  void whenValuesOfActualAndExpectedAreDifferentThenThrowException() {
    assertThrows(
        DefaultException.class,
        () -> commonInternalCheckService.shouldBeMatch(1, 2)
    );
  }

  @Test
  void whenValuesOfActualAndExpectedEqualThenWillDoNothing() {
    commonInternalCheckService.shouldBeMatch(1, 1);
  }
}
