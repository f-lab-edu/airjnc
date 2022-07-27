package com.airjnc.user.util;

public interface UserRegex {

  String password = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#!~$%^&-+=()])(?=\\S+$).{8,16}$";
}

