package com.airjnc.user.dao;

import com.airjnc.user.domain.Gender;
import com.airjnc.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import util.UserFixture;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class UserDaoTest {

    @Autowired
    private UserDAO userDAO;

    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {

        this.user1 = UserFixture.getUserBuilder()
            .id(null)
            .email("test@naver.com")
            .build();

        this.user2 = UserFixture.getUserBuilder()
            .id(null)
            .email("test2@naver.com")
            .name("testUser2")
            .gender(Gender.FEMALE)
            .build();
    }

    @Test
    @DisplayName("Insert 후 이메일로 유저찾기")
    public void addAndGetByEmail() {
        userDAO.insertUser(user1);
        assertThat(user1.getId()).isEqualTo(userDAO.selectUserByEmail(user1.getEmail()).get().getId());
    }

    @Test
    @DisplayName("Insert 후 ID로 유저찾기")
    public void addAndGetById() {
        userDAO.insertUser(user1);
        assertThat(user1.getEmail()).isEqualTo(userDAO.selectUserById(user1.getId()).get().getEmail());


    }

    @Test
    @DisplayName("Insert 후 AutoIncrement 자동증가값 가져오기")
    public void getAutoIncrementKey() {
        userDAO.insertUser(user1);
        userDAO.insertUser(user2);
        assertThat(user1.getId()).isEqualTo(userDAO.selectUserByEmail("test@naver.com").get().getId());
        assertThat(user2.getId()).isEqualTo(userDAO.selectUserByEmail("test2@naver.com").get().getId());
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
        Optional<User> dbUser = userDAO.selectUserByEmail("test@naver.com");
        assertThat(dbUser.get().getCreatedAt()).isNotNull();
        assertThat(dbUser.get().getUpdatedAt()).isNotNull();
        assertThat(dbUser.get().isActive()).isEqualTo(true);
    }


}