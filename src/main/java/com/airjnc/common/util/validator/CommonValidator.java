package com.airjnc.common.util.validator;

import org.springframework.stereotype.Component;

@Component
public class CommonValidator {
    public void validateEqual(int actual, int expected) {
        if (actual != expected) {
            throw new RuntimeException(String.format("expected = %d, but actual = %d", 1, 2));
        }
    }
}
