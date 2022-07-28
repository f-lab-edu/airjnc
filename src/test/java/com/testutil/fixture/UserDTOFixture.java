package com.testutil.fixture;

import com.airjnc.user.dto.response.UserDTO;
import com.testutil.testdata.TestUser;

public class UserDTOFixture {

  public static UserDTO.UserDTOBuilder getBuilder() {
    return UserDTO.builder()
        .id(TestUser.ID)
        .email(TestUser.EMAIL)
        .name(TestUser.NAME)
        .gender(TestUser.GENDER)
        .phoneNumber(TestUser.PHONE_NUMBER)
        .address(TestUser.ADDRESS)
        .birthDate(TestUser.BIRTH_DATE);
  }
}
