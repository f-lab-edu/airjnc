package com.testutil.fixture.user;

import com.airjnc.user.domain.Gender;
import com.airjnc.user.dto.request.UserCreateReq;
import java.time.LocalDate;

public class UserCreateReqFixture {

  public static final String EMAIL = "test2@naver.com";

  public static final String PASSWORD = "q1w2e3r4!";

  public static final String NAME = "testUser2";

  public static final Gender GENDER = Gender.FEMALE;

  public static final LocalDate BIRTHDATE = LocalDate.of(2020, 1, 1);

  public static UserCreateReq.UserCreateReqBuilder getBuilder() {
    return UserCreateReq.builder()
        .email(EMAIL)
        .password(PASSWORD)
        .name(NAME)
        .gender(GENDER)
        .birthDate(BIRTHDATE);
  }
}
