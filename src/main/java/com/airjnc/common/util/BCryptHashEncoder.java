package com.airjnc.common.util;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptHashEncoder {

    public static String encode(String plain) {
        return BCrypt.hashpw(plain, BCrypt.gensalt());
    }

    public static boolean isMatch(String plain, String hash) {
        return BCrypt.checkpw(plain, hash);
    }
}
