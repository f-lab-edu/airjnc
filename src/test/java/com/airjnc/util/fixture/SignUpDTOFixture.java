package com.airjnc.util.fixture;

import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.SignUpDTO;

public class SignUpDTOFixture {
    public static final String EMAIL = "test@naver.com";
    public static final String PASSWORD = "q1w2e3r4!";
    public static final String NAME = "testUser";
    public static final UserEntity.Gender GENDER = UserEntity.Gender.MALE;

    public static SignUpDTO.SignUpDTOBuilder getBuilder() {
        return SignUpDTO.builder()
            .email(EMAIL)
            .password(PASSWORD)
            .name(NAME)
            .gender(GENDER);
    }
}
