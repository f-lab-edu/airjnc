package com.airjnc.user.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.anyInt;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import com.airjnc.common.exception.NotFoundException;
import com.airjnc.common.service.CommonInternalCheckService;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.dao.mapper.UserMapper;
import com.airjnc.user.domain.Gender;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UpdatePasswordByEmailDTO;
import com.airjnc.user.dto.request.CreateDTO;
import com.airjnc.user.dto.request.FindEmailDTO;
import com.airjnc.user.util.UserModelMapper;
import com.testutil.annotation.MybatisTest;
import com.testutil.fixture.UserEntityFixture;
import com.testutil.fixture.UpdatePasswordByEmailDTOFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@MybatisTest
class MybatisUserRepositoryTest {

  UserRepository userRepository;

  @Autowired
  UserMapper userMapper;

  @Spy
  CommonInternalCheckService commonInternalCheckService;

  @Mock
  UserModelMapper userModelMapper;

  @BeforeEach
  void beforeEach() {
    userRepository = new MybatisUserRepository(userMapper, commonInternalCheckService,
        userModelMapper);
  }

  @Test
  void findById() {
    //given
    UserEntity user = UserEntityFixture.getBuilder().build();
    //when
    UserEntity findUser = userRepository.findById(user.getId());
    //then
    assertThat(findUser.getId()).isSameAs(user.getId());
  }

  @Test
  void findByEmail() {
    //given
    UserEntity user = UserEntityFixture.getBuilder().build();
    //when
    UserEntity findUser = userRepository.findByEmail(user.getEmail());
    //then
    assertThat(findUser.getId()).isSameAs(user.getId());
    assertThat(findUser.getEmail()).isEqualTo(user.getEmail());
  }

  @Test
  void getEmail() {
    //given
    UserEntity user = UserEntityFixture.getBuilder().build();
    FindEmailDTO findEmailDTO = FindEmailDTO.builder()
        .name(user.getName())
        .birthDate(user.getBirthDate().toString())
        .build();
    //when
    String email = userRepository.getEmail(findEmailDTO);
    //then
    assertThat(email).isEqualTo(user.getEmail());
  }

  @Test
  @Transactional
  void save() {
    //given
    CreateDTO createDTO = CreateDTO.builder()
        .email("abc@google.com")
        .password("q1w2e3")
        .name("abcUser")
        .gender(Gender.FEMALE)
        .build();
    //when
    userRepository.save(createDTO);
    //then
    UserEntity byEmail = userRepository.findByEmail(createDTO.getEmail());
    assertThat(byEmail.getName()).isEqualTo(createDTO.getName());
    then(commonInternalCheckService).should(times(1)).shouldBeMatch(1, 1);
  }

  @Test
  @Transactional
  void remove() {
    //given
    UserEntity user = UserEntityFixture.getBuilder().build();
    //when
    userRepository.remove(user.getId());
    //then
    Assertions.assertThrows(
        NotFoundException.class,
        () -> userRepository.findByEmail(user.getEmail())
    );
    then(commonInternalCheckService).should(times(1)).shouldBeMatch(anyInt(), anyInt());
  }

  @Test
  @Transactional
  void updatePasswordByEmail() {
    //given
    UpdatePasswordByEmailDTO updatePasswordByEmailDTO = UpdatePasswordByEmailDTOFixture.getBuilder().build();
    //when
    userRepository.updatePasswordByEmail(updatePasswordByEmailDTO);
    //then
    UserEntity byEmail = userRepository.findByEmail(updatePasswordByEmailDTO.getEmail());
    assertThat(byEmail.getPassword()).isEqualTo(updatePasswordByEmailDTO.getPassword());
    then(commonInternalCheckService).should(times(1)).shouldBeMatch(anyInt(), anyInt());
  }
}

