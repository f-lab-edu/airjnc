package com.testutil.fixture;

import com.airjnc.user.dto.request.LogInDTO;
import com.testutil.testdata.TestUser;

public class LogInDTOFixture {


  public static LogInDTO.LogInDTOBuilder getBuilder() {
    return LogInDTO.builder().email(TestUser.EMAIL).password(TestUser.PASSWORD);
  }
}
