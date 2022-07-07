package com.airjnc.user.repository;

import com.airjnc.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import util.UserFixture;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles(profiles = "local")
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user1;

    @BeforeEach
    public void setUp() {
        this.user1 = UserFixture.getUserBuilder()
            .id(null)
            .email("test@naver.com")
            .build();
    }

    @Test
    public void repositoryTest() {
        User user = userRepository.insertUser(user1);
        assertThat(user1.getEmail()).isEqualTo(userRepository.selectUserById(user1.getId()).getEmail());
        assertThat(user1.getId()).isEqualTo(userRepository.selectUserByEmail(user1.getEmail()).getId());
    }

}