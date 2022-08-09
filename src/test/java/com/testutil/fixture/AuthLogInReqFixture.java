package com.testutil.fixture;

import com.airjnc.user.dto.request.AuthLogInReq;

public class AuthLogInReqFixture {

  public static final String EMAIL = "test@naver.com";

  public static final String PASSWORD = "q1w2e3r4!";

  public static AuthLogInReq.AuthLogInReqBuilder getBuilder() {
    return AuthLogInReq.builder().email(EMAIL).password(PASSWORD);
  }
}
