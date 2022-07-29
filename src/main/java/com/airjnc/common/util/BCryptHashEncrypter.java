package com.airjnc.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BCryptHashEncrypter {

  public static String encrypt(String plain) {
    return BCrypt.hashpw(plain, BCrypt.gensalt());
  }

  public static boolean isMatch(String plain, String hash) {
    return BCrypt.checkpw(plain, hash);
  }
}
