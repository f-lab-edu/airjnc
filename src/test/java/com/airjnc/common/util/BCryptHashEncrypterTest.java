package com.airjnc.common.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BCryptHashEncrypterTest {
    @Test
    void plainShouldBeHashed() {
        //given
        String plain = "q1w2e3r4!";
        //when
        String hash = BCryptHashEncrypter.encrypt(plain);
        //then
        assertThat(plain).isNotEqualTo(hash);
    }

    @Test
    void plainShouldBeMatchToB() {
        //given
        String plain = "q1w2e3r4!";
        String hash = BCryptHashEncrypter.encrypt(plain);
        //when
        boolean match = BCryptHashEncrypter.isMatch(plain, hash);
        //then
        assertThat(match).isTrue();
    }
}
