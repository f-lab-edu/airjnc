package com.airjnc.user.mapper;

import com.airjnc.user.domain.User;
import com.airjnc.user.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class UserMapperTest {

    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    private User user;
    private UserDTO userDTO;

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
    }

    @Test
    @DisplayName("User to UserDTO")
    public void userToUserDTO() {
        assertThat(userMapper.toUserDTO(user)).isInstanceOf(UserDTO.class);
        assertThat(userMapper.toUserDTO(user).toString()).isEqualTo(userDTO.toString());
    }

    @Test
    @DisplayName("UserDTO to User")
    public void userDTOToUser() {
        assertThat(userMapper.toUser(userDTO)).isInstanceOf(User.class);
        assertThat(userMapper.toUser(userDTO).toString()).isEqualTo(user.toString());

    }


}