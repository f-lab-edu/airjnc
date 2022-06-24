package com.airjnc.user.dao.impl;

import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.dao.mapper.UserMapper;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.util.annotation.DaoTest;
import com.airjnc.util.fixture.UserEntityFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@DaoTest
class MybatisUserRepositoryTest {
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;

    @BeforeEach
    void beforeEach() {
        this.userRepository = new MybatisUserRepository(userMapper, new ModelMapper());
    }

    @Test
    @DisplayName("findById) id를 이용하여 조회 메소드[findById]를 사용할 경우, 해당 id에 대한 데이터가 반환되어야 한다.")
    void findById() {
        //given
        UserEntity user = UserEntityFixture.getBuilder().build();
        //when
        Optional<UserEntity> findUser = this.userRepository.findById(user.getId());
        //then
        assertThat(findUser.isPresent()).isTrue();
        UserEntity userEntity = findUser.orElseThrow();
        assertThat(userEntity.getId()).isSameAs(user.getId());
        assertThat(userEntity.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("findByEmail) email을 이용하여 조회 메소드[findByEmail]를 사용할 경우, 해당 id에 대한 데이터가 반환되어야 한다.")
    void findByEmail() {
        //given
        UserEntity user = UserEntityFixture.getBuilder().build();
        //when
        Optional<UserEntity> findUser = this.userRepository.findByEmail(user.getEmail());
        //then
        assertThat(findUser.isPresent()).isTrue();
        UserEntity userEntity = findUser.orElseThrow();
        assertThat(userEntity.getId()).isSameAs(user.getId());
        assertThat(userEntity.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @Transactional
    @DisplayName("save) 유저가 저장되어야 하고, 저장된 유저의 아이디를 통해 조회를 할 경우, 저장된 유저가 조회되어야 한다.")
    void save() {
        //given
        String email = "abc@google.com";
        String password = "q1w2e3";
        String name = "abcUser";
        UserEntity.Gender gender = UserEntity.Gender.FEMALE;
        SignUpDTO signUpDTO = SignUpDTO.builder().email(email).password(password).name(name).gender(gender).build();
        //when
        UserEntity savedUser = this.userRepository.save(signUpDTO);
        Optional<UserEntity> findUser = this.userRepository.findById(savedUser.getId());
        //then
        assertThat(findUser.isPresent()).isTrue();
        UserEntity userEntity = findUser.orElseThrow();
        assertThat(userEntity.getId()).isEqualTo(savedUser.getId());
        assertThat(userEntity.getEmail()).isEqualTo(savedUser.getEmail());
        assertThat(userEntity.getName()).isEqualTo(savedUser.getName());
        assertThat(userEntity.getGender()).isEqualTo(savedUser.getGender());
    }
}

