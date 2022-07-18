package util;

import com.airjnc.user.domain.Gender;
import com.airjnc.user.domain.User;
import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.dto.response.UserDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserFixture {

    public static final Long ID = 1L;
    public static final String EMAIL = "test@naver.com";
    public static final String PASSWORD = "ch12345678";
    public static final String NAME = "testUser";
    public static final Gender GENDER = Gender.MALE;
    public static final String PHONE_NUMBER = "010-2222-3333";
    public static final String ADDRESS = "서울시 강동구";
    public static final boolean ACTIVE = true;
    public static final LocalDate BIRTH_DATE = LocalDate.of(1995, 1, 25);
    public static final LocalDateTime CREATED_AT = LocalDateTime.of(2022, 1, 1, 9, 0);
    public static final LocalDateTime UPDATED_AT = LocalDateTime.of(2022, 1, 1, 9, 0);


    public static User.UserBuilder getUserBuilder() {
        return User.builder()
            .id(ID)
            .email(EMAIL)
            .password(PASSWORD)
            .name(NAME)
            .gender(GENDER)
            .phoneNumber(PHONE_NUMBER)
            .address(ADDRESS)
            .active(ACTIVE)
            .birthDate(BIRTH_DATE)
            .createdAt(CREATED_AT)
            .updatedAt(UPDATED_AT)
            ;
    }

    public static UserDTO.UserDTOBuilder getUserDTOBuilder() {
        return UserDTO.builder()
            .id(ID)
            .email(EMAIL)
            .password(PASSWORD)
            .name(NAME)
            .gender(GENDER)
            .phoneNumber(PHONE_NUMBER)
            .address(ADDRESS)
            .active(ACTIVE)
            .birthDate(BIRTH_DATE)
            .createdAt(CREATED_AT)
            .updatedAt(UPDATED_AT)
            ;
    }

    public static SignUpDTO.SignUpDTOBuilder getSignUpDTOBuilder() {
        return SignUpDTO.builder()
            .email(EMAIL)
            .password(PASSWORD)
            .name(NAME)
            .gender(GENDER)
            .phoneNumber(PHONE_NUMBER)
            .address(ADDRESS)
            .birthDate(BIRTH_DATE)
            ;
    }


}
