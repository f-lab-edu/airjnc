package com.airjnc.user.dao.impl;

import com.airjnc.common.util.validator.CommonValidator;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.dao.mapper.UserMapper;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.util.annotation.MybatisTest;
import com.airjnc.util.fixture.UserEntityFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;


@MybatisTest
class MybatisUserRepositoryTest {
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;
    @Spy
    CommonValidator commonValidator;

    @BeforeEach
    void beforeEach() {
        userRepository = new MybatisUserRepository(userMapper, new ModelMapper(), commonValidator);
    }

    @Test
    void findById() {
        //given
        UserEntity user = UserEntityFixture.getBuilder().build();
        //when
        Optional<UserEntity> findUser = userRepository.findById(user.getId());
        //then
        assertThat(findUser.orElseThrow().getId()).isSameAs(user.getId());
    }

    @Test
    void findByEmail() {
        //given
        UserEntity user = UserEntityFixture.getBuilder().build();
        //when
        Optional<UserEntity> findUser = userRepository.findByEmail(user.getEmail());
        //then
        assertThat(findUser.isPresent()).isTrue();
        UserEntity userEntity = findUser.orElseThrow();
        assertThat(userEntity.getId()).isSameAs(user.getId());
        assertThat(userEntity.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @Transactional
    void save() {
        //given
        String email = "abc@google.com";
        String password = "q1w2e3";
        String name = "abcUser";
        UserEntity.Gender gender = UserEntity.Gender.FEMALE;
        SignUpDTO signUpDTO = SignUpDTO.builder().email(email).password(password).name(name).gender(gender).build();
        //when
        UserEntity savedUser = userRepository.save(signUpDTO);
        Optional<UserEntity> findUser = userRepository.findById(savedUser.getId());
        //then
        assertThat(findUser.orElseThrow().getId()).isEqualTo(savedUser.getId());
        then(commonValidator).should(times(1)).validateEqual(anyInt(), anyInt());
    }

    @Test
    @Transactional
    void remove() {
        //given
        UserEntity userEntity = UserEntityFixture.getBuilder().build();
        //when
        userRepository.remove(userEntity.getId());
        //then
        then(commonValidator).should(times(1)).validateEqual(anyInt(), anyInt());
    }
}
