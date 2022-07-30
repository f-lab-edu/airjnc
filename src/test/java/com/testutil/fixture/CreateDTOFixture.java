package com.testutil.fixture;

import com.airjnc.user.dto.request.UserCreateDTO;
import com.airjnc.user.domain.Gender;

public class CreateDTOFixture {

  public static final String EMAIL = "test2@naver.com";

  public static final String PASSWORD = "q1w2e3r4!";

  public static final String NAME = "testUser2";

  public static final Gender GENDER = Gender.FEMALE;

  public static final String birthDate = "2021-02-02";

  public static UserCreateDTO.UserCreateDTOBuilder getBuilder() {
    return UserCreateDTO.builder()
        .email(EMAIL)
        .password(PASSWORD)
        .name(NAME)
        .gender(GENDER)
        .birthDate(birthDate);
  }
}
