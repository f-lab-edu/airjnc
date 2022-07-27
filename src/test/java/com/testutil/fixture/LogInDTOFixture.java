package com.testutil.fixture;

import com.airjnc.user.dto.request.LogInDTO;

public class LogInDTOFixture {


  public static LogInDTO.LogInDTOBuilder getBuilder() {
    return LogInDTO.builder().email(UserEntityFixture.EMAIL).password(UserEntityFixture.PASSWORD);
  }
}
