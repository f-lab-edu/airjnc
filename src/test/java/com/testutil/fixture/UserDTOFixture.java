package com.testutil.fixture;

import com.airjnc.user.dto.response.UserDTO;

public class UserDTOFixture {

  public static UserDTO.UserDTOBuilder getBuilder() {
    return UserDTO.builder()
        .id(UserEntityFixture.ID)
        .email(UserEntityFixture.EMAIL)
        .name(UserEntityFixture.NAME)
        .gender(UserEntityFixture.GENDER)
        .phoneNumber(UserEntityFixture.PHONE_NUMBER)
        .address(UserEntityFixture.ADDRESS)
        .birthDate(UserEntityFixture.BIRTH_DATE);
  }
}
