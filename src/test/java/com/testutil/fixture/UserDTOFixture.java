package com.testutil.fixture;

import com.airjnc.user.domain.Gender;
import com.airjnc.user.dto.response.UserDTO;

import java.time.LocalDate;

public class UserDTOFixture {
    public static final Long ID = 2L;
    public static final String EMAIL = "test@naver.com";
    public static final String NAME = "testUser";
    public static final Gender GENDER = Gender.MALE;
    public static final String PHONE_NUMBER = "010-1111-2222";
    public static final String ADDRESS = "서울 강남구";
    public static final LocalDate BIRTH_DATE = LocalDate.of(2000, 5, 15);

    public static UserDTO.UserDTOBuilder getBuilder() {
        return UserDTO.builder()
            .id(ID)
            .email(EMAIL)
            .name(NAME)
            .gender(GENDER)
            .phoneNumber(PHONE_NUMBER)
            .address(ADDRESS)
            .birthDate(BIRTH_DATE);
    }
}
