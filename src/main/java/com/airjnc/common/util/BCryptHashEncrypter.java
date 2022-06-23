package com.airjnc.common.util;

import org.mindrot.jbcrypt.BCrypt;

public final class BCryptHashEncrypter {
    private BCryptHashEncrypter() {
        // 생성 방지
    }

    public static String encrypt(String plain) {
        return BCrypt.hashpw(plain, BCrypt.gensalt());
    }

    public static boolean isMatch(String plain, String hash) {
        return BCrypt.checkpw(plain, hash);
    }
}
