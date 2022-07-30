package com.testutil.fixture;

import com.airjnc.user.dto.request.AuthLogInDTO;
import com.testutil.testdata.TestUser;

public class LogInDTOFixture {

  public static AuthLogInDTO.AuthLogInDTOBuilder getBuilder() {
    return AuthLogInDTO.builder().email(TestUser.EMAIL).password(TestUser.PASSWORD);
  }
}
