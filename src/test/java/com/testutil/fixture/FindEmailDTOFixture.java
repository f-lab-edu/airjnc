package com.testutil.fixture;

import com.airjnc.user.dto.request.UserFindEmailDTO;
import com.testutil.testdata.TestUser;

public class FindEmailDTOFixture {

  public static UserFindEmailDTO.UserFindEmailDTOBuilder getBuilder() {
    return UserFindEmailDTO.builder()
        .name(TestUser.NAME)
        .birthDate(TestUser.BIRTH_DATE.toString());
  }
}
