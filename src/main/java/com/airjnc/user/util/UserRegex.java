package com.airjnc.user.util;

public interface UserRegex {

  String PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#!~$%^&-+=()])(?=\\S+$).{8,16}$";

  String PHONE = "(01[016789])(\\d{3,4})(\\d{4})";
}

