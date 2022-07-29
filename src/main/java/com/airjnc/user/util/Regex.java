package com.airjnc.user.util;

public interface Regex {

  interface Password {

    String format = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#!~$%^&-+=()])(?=\\S+$).{8,16}$";
  }
}

