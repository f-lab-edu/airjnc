package com.airjnc.user.dao;

import com.airjnc.user.dto.UserDTO;
import com.airjnc.user.repository.UserRepository;
import com.airjnc.user.repository.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserDaoTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        UserDTO userDTO = new UserDTO(1L,"test@naver.com","1234","창훈");
    }
    
    @Test
    public void findUserByIdTest(){
        assertThat(userRepository.findUserById(1L).name).isEqualTo("창훈");

    }

}