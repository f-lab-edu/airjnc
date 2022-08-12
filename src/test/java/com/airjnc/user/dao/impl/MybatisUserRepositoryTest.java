package com.airjnc.user.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.anyInt;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import com.airjnc.common.exception.NotFoundException;
import com.airjnc.common.service.CommonCheckService;
import com.airjnc.user.dao.UserMapper;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.Gender;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UserDto;
import com.airjnc.user.dto.UserDto.UserStatus;
import com.airjnc.user.dto.UserSaveDto;
import com.airjnc.user.dto.request.UserCreateReq;
import com.airjnc.user.util.UserModelMapper;
import com.testutil.annotation.MybatisTest;
import com.testutil.testdata.TestUser;
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
  CommonCheckService commonCheckService;

  @Mock
  UserModelMapper userModelMapper;

  UserEntity testUser;

  @BeforeEach
  void beforeEach() {
    userRepository = new MybatisUserRepository(userMapper, commonCheckService,
        userModelMapper);
    testUser = TestUser.getBuilder().build();
  }

  @Test
  @Transactional
  void delete() {
    //when
    userRepository.delete(testUser.getId());
    //then
    Assertions.assertThrows(
        NotFoundException.class,
        () -> userRepository.findById(testUser.getId(), UserStatus.ACTIVE)
    );
    then(commonCheckService).should(times(1)).shouldBeMatch(anyInt(), anyInt());
  }

  @Test
  void findById() {

    //when
    UserEntity findUser = userRepository.findById(testUser.getId(), UserStatus.ACTIVE);
    //then
    assertThat(findUser.getId()).isSameAs(testUser.getId());
  }

  @Test
  @Transactional
  void save() {
    //given
    UserSaveDto userSaveDTO = UserCreateReq.builder()
        .email("abc@google.com")
        .password("q1w2e3")
        .name("abcUser")
        .gender(Gender.FEMALE)
        .build()
        .toSaveDTO("hash");
    //when
    userRepository.create(userSaveDTO);
    //then
    UserEntity byEmail = userRepository.findByWhere(
        UserDto.builder().email(userSaveDTO.getEmail()).status(UserStatus.ALL).build());
    assertThat(byEmail.getName()).isEqualTo(userSaveDTO.getName());
    then(commonCheckService).should(times(1)).shouldBeMatch(1, 1);
  }

  @Test
  @Transactional
  void update() {
    //given
    String passwrod = "q1w2e3r4t5!@#";
    UserEntity userEntity = userRepository.findById(TestUser.ID, UserStatus.ALL);
    userEntity.setPassword(passwrod);
    //when
    userRepository.save(userEntity);
    //then
    then(commonCheckService).should(times(1)).shouldBeMatch(1, 1);
  }
}

