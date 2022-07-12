package com.testutil.fixture;

import com.airjnc.user.dto.request.LogInDTO;

public class LogInDTOFixture {
    public static final String EMAIL = "test@naver.com";
    public static final String PASSWORD = "q1w2e3r4!";

    public static LogInDTO.LogInDTOBuilder getBuilder() {
        return LogInDTO.builder().email(EMAIL).password(PASSWORD);
    }
}
