package com.testutil.fixture;

import com.airjnc.user.dto.request.FindEmailDTO;
import com.testutil.testdata.TestUser;

public class FindEmailDTOFixture {

  public static FindEmailDTO.FindEmailDTOBuilder getBuilder() {
    return FindEmailDTO.builder()
        .name(TestUser.NAME)
        .birthDate(TestUser.BIRTH_DATE.toString());
  }
}
