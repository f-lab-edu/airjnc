package com.airjnc.common.service;

import com.airjnc.common.exception.DefaultException;
import com.airjnc.common.util.factory.ErrorsFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonCheckService {

  public void shouldBeMatch(int actual, int expected) {
    if (actual == expected) {
      return;
    }
    /*
    1. CommonCheckService.isNotMatch
    2. CommonCheckService
     */
    throw new DefaultException(
        ErrorsFactory.createAndReject(this.getClass().getSimpleName(), "shouldBeMatch", new Object[]{actual, expected})
    );
  }
}
