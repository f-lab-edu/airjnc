package com.airjnc.common.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BCryptHashEncoderTest {

    @Test
    void testEncodeAndIsMatch() {
        String rawString = "rawraw";
        String encodeString = BCryptHashEncoder.encode(rawString);
        System.out.println(encodeString);
        Assertions.assertThat(BCryptHashEncoder.isMatch(rawString, encodeString)).isTrue();
    }
}