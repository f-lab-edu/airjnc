package com.airjnc.common.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class CommonHashService {

  public String encrypt(String plain) {
    return BCrypt.hashpw(plain, BCrypt.gensalt());
  }

  public boolean isMatch(String plain, String hash) {
    return BCrypt.checkpw(plain, hash);
  }
}
