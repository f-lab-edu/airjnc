package com.testutil.fixture;

import com.airjnc.user.dto.request.FindEmailDTO;

public class FindEmailDTOFixture {

  public static FindEmailDTO.FindEmailDTOBuilder getBuilder() {
    return FindEmailDTO.builder()
        .name(UserEntityFixture.NAME)
        .birthDate(UserEntityFixture.BIRTH_DATE.toString());
  }
}
