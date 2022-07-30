package com.testutil.fixture;

import com.airjnc.user.dto.UpdatePasswordByEmailDTO;
import com.testutil.testdata.TestUser;

public class UpdatePasswordByEmailDTOFixture {

  public static final String PASSWORD = "q1w2e3r4t5r6!";

  public static UpdatePasswordByEmailDTO.UpdatePasswordByEmailDTOBuilder getBuilder() {
    return UpdatePasswordByEmailDTO.builder().email(TestUser.EMAIL).password(PASSWORD);
  }
}
