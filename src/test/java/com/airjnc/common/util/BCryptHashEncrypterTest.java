package com.airjnc.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BCryptHashEncrypterTest {
    @Test
    @DisplayName("평문이 정상적으로 해시로 변경되어야 하며, 평문과 해시를 비교할 경우 동일하다면 TRUE를 반환해야 한다.")
    void hashTest() {
        //given
        String plain = "q1w2e3r4!";
        String hash = BCryptHashEncrypter.encrypt(plain);
        //when
        boolean match = BCryptHashEncrypter.isMatch(plain, hash);
        //then
        assertThat(plain).isNotEqualTo(hash);
        assertThat(match).isTrue();
    }
}