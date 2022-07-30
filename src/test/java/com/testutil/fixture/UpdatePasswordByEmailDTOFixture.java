package com.testutil.fixture;

import com.airjnc.user.dto.UserUpdatePwdByEmailDTO;
import com.testutil.testdata.TestUser;

public class UpdatePasswordByEmailDTOFixture {

  public static final String PASSWORD = "q1w2e3r4t5r6!";

  public static UserUpdatePwdByEmailDTO.UserUpdatePwdByEmailDTOBuilder getBuilder() {
    return UserUpdatePwdByEmailDTO.builder().email(TestUser.EMAIL).password(PASSWORD);
  }
}
