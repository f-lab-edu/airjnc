package com.airjnc.user.mapper;

import com.airjnc.user.domain.User;
import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.dto.response.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Spy;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class UserMapperTest {

    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    private User user;
    private UserDTO userDTO;
    private SignUpDTO signUpDTO;

    @BeforeEach
    public void setUp() {
        this.user = User.builder()
            .email("test@naver.com")
            .password("1234")
            .name("창훈")
            .gender(User.Gender.MALE)
            .phoneNumber("010-2222-3333")
            .address("서울시 강동구")
            .birthDate(LocalDate.of(1995, 1, 25))
            .build();

        this.userDTO = UserDTO.builder()
            .email("test@naver.com")
            .password("1234")
            .name("창훈")
            .gender(UserDTO.Gender.MALE)
            .phoneNumber("010-2222-3333")
            .address("서울시 강동구")
            .birthDate(LocalDate.of(1995, 1, 25))
            .build();
        
        this.signUpDTO = SignUpDTO.builder()
            .email("test@naver.com")
            .password("1234")
            .name("창훈")
            .gender(SignUpDTO.Gender.MALE)
            .phoneNumber("010-2222-3333")
            .address("서울시 강동구")
            .birthDate(LocalDate.of(1995, 1, 25))
            .build();
    }

    @Test
    @DisplayName("User to UserDTO")
    public void userToUserDTO() {
        assertThat(userMapper.userToUserDTO(user)).isInstanceOf(UserDTO.class);
        assertThat(userMapper.userToUserDTO(user).toString()).isEqualTo(userDTO.toString());
    }

    @Test
    @DisplayName("UserDTO to User")
    public void userDTOToUser() {
        assertThat(userMapper.userDTOtoUser(userDTO)).isInstanceOf(User.class);
        assertThat(userMapper.userDTOtoUser(userDTO).toString()).isEqualTo(user.toString());
    }

    @Test
    @DisplayName("SignUpDTO to User")
    public void signUpDTOToUser() {
        assertThat(userMapper.userDTOtoUser(userDTO)).isInstanceOf(User.class);
        assertThat(userMapper.userDTOtoUser(userDTO).toString()).isEqualTo(user.toString());
    }


}