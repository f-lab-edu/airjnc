package com.airjnc.user.dao;

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


@SpringBootTest
@ActiveProfiles("local")
@Transactional
class UserDaoTest {

    @Autowired
    private UserDAO userDAO;

    private User user1;
    private User user2;

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

        this.user2 = User.builder()
            .email("test2@naver.com")
            .password("1234")
            .name("창훈2")
            .gender(User.Gender.FEMALE)
            .phoneNumber("010-2222-3334")
            .address("서울시 강동구")
            .active(true)
            .birthDate(LocalDate.of(1995, 1, 25))
            .build();
    }

    @Test
    @DisplayName("Insert 후 이메일로 유저찾기")
    public void addAndGetByEmail() {
        userDAO.insertUser(user1);
        assertThat(user1.getId()).isEqualTo(userDAO.selectUserByEmail("test@naver.com").getId());
    }

    @Test
    @DisplayName("Insert 후 ID로 유저찾기")
    public void addAndGetById() {
        userDAO.insertUser(user1);
        assertThat(user1.getEmail()).isEqualTo(userDAO.selectUserById(user1.getId()).getEmail());
    }

    @Test
    @DisplayName("Insert 후 AutoIncrement 자동증가값 가져오기")
    public void getAutoIncrementKey() {
        userDAO.insertUser(user1);
        userDAO.insertUser(user2);
        assertThat(user1.getId()).isEqualTo(userDAO.selectUserByEmail("test@naver.com").getId());
        assertThat(user2.getId()).isEqualTo(userDAO.selectUserByEmail("test2@naver.com").getId());
    }

    @Test
    @DisplayName("Insert 후 AutoIncrement 자동증가여부 확인")
    public void plusAutoIncrementKey() {
        userDAO.insertUser(user1);
        userDAO.insertUser(user2);
        assertThat(user1.getId() + 1).isEqualTo(user2.getId());
    }

    @Test
    @DisplayName("Insert 후 DB Default값 정상 설정 확인")
    public void checkDBDefault() {
        userDAO.insertUser(user1);
        User dbUser = userDAO.selectUserByEmail("test@naver.com");
        assertThat(dbUser.getCreatedAt()).isNotNull();
        assertThat(dbUser.getUpdatedAt()).isNotNull();
        assertThat(dbUser.isActive()).isEqualTo(true);
    }


}