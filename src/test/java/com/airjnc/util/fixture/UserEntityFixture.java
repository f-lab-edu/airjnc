package com.airjnc.util.fixture;

import com.airjnc.user.domain.UserEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserEntityFixture {

    public static final Long ID = 1L;
    public static final String EMAIL = "test@naver.com";
    public static final String PASSWORD = "q1w2e3r4t5!";
    public static final String NAME = "testUser";
    public static final UserEntity.Gender GENDER = UserEntity.Gender.MALE;
    public static final String PHONE_NUMBER = "010-1111-2222";
    public static final String ADDRESS = "서울 강남구";
    public static final boolean IS_ACTIVE = true;
    public static final LocalDate BIRTH_DATE = LocalDate.of(2000, 5, 15);
    public static final LocalDateTime CREATED_AT = LocalDateTime.of(2022, 1, 1, 9, 0);
    public static final LocalDateTime UPDATED_AT = LocalDateTime.of(2022, 1, 1, 9, 0);

    public static UserEntity.UserEntityBuilder getBuilder() {
        return UserEntity.builder()
            .id(ID)
            .email(EMAIL)
            .password(PASSWORD)
            .name(NAME)
            .gender(GENDER)
            .phoneNumber(PHONE_NUMBER)
            .address(ADDRESS)
            .isActive(IS_ACTIVE)
            .birthDate(BIRTH_DATE)
            .createdAt(CREATED_AT)
            .updatedAt(UPDATED_AT)
            .deletedAt(null);
    }
}
