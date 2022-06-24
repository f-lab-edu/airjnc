package com.airjnc.user.repository;

import com.airjnc.user.dao.UserDAO;
import com.airjnc.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(profiles = "local")
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user1;

    @BeforeEach
    public void setUp() {
        this.user1 = User.builder()
            .email("test@naver.com")
            .password("1234")
            .name("창훈")
            .gender(User.Gender.MALE)
            .phoneNumber("010-2222-3333")
            .address("서울시 강동구")
            .active(true)
            .birthDate(LocalDate.of(1995, 1, 25))
            .build();
    }

    @Test
    public void repositoryTest() {
        userRepository.insertUser(user1);
        assertThat(user1.getEmail()).isEqualTo(userRepository.selectUserById(user1.getId()).getEmail());
        assertThat(user1.getId()).isEqualTo(userRepository.selectUserByEmail("test@naver.com").getId());
    }

}