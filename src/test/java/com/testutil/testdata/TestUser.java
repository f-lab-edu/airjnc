package com.testutil.testdata;

import com.airjnc.user.domain.Gender;
import com.airjnc.user.domain.UserEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestUser {

  public static final Long ID = 1L;

  public static final String EMAIL = "test@naver.com";

  public static final String PASSWORD = "q1w2e3r4t5!";

  public static final String NAME = "testUser";

  public static final Gender GENDER = Gender.MALE;

  public static final String ADDRESS = "서울 강남구";

  public static final LocalDate BIRTH_DATE = LocalDate.of(2020, 1, 1);

  public static final LocalDateTime CREATED_AT = LocalDateTime.of(2022, 1, 1, 9, 0);

  public static UserEntity.UserEntityBuilder getBuilder() {
    return UserEntity.builder()
        .id(ID)
        .email(EMAIL)
        .password(PASSWORD)
        .name(NAME)
        .gender(GENDER)
        .address(ADDRESS)
        .birthDate(BIRTH_DATE);
  }
}
