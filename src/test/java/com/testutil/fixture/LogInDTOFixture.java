package com.testutil.fixture;

import com.airjnc.user.dto.request.UserLogInReq;
import com.testutil.testdata.TestUser;

public class LogInDTOFixture {

  public static UserLogInReq.UserLogInReqBuilder getBuilder() {
    return UserLogInReq.builder().email(TestUser.EMAIL).password(TestUser.PASSWORD);
  }
}
