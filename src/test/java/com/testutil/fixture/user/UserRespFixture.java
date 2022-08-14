package com.testutil.fixture.user;

import com.airjnc.user.dto.response.UserResp;
import com.testutil.testdata.TestUser;

public class UserRespFixture {

  public static UserResp.UserRespBuilder getBuilder() {
    return UserResp.builder()
        .id(TestUser.ID)
        .email(TestUser.EMAIL)
        .name(TestUser.NAME)
        .gender(TestUser.GENDER)
        .phoneNumber(TestUser.PHONE_NUMBER)
        .address(TestUser.ADDRESS)
        .birthDate(TestUser.BIRTH_DATE);
  }
}
