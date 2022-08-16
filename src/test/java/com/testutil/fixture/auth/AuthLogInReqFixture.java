package com.testutil.fixture.auth;

import com.airjnc.auth.dto.request.AuthLogInReq;
import com.testutil.testdata.TestUser;

public class AuthLogInReqFixture {

  public static AuthLogInReq.AuthLogInReqBuilder getBuilder() {
    return AuthLogInReq.builder().email(TestUser.EMAIL).password(TestUser.PASSWORD);
  }
}
