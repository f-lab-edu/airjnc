package com.airjnc.user.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.anyInt;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import com.airjnc.common.service.CommonCheckService;
import com.airjnc.user.dao.UserMapper;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.Gender;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UserSaveDto;
import com.airjnc.user.util.UserModelMapper;
import com.testutil.annotation.MybatisTest;
import com.testutil.testdata.TestUser;
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

  @BeforeEach
  void beforeEach() {
    userRepository = new MybatisUserRepository(userMapper, commonCheckService, userModelMapper);
  }

  @Test
  void findByEmail() {
    //given
    UserEntity user = TestUser.getBuilder().build();
    //when
    UserEntity findUser = userRepository.findByEmail(user.getEmail());
    //then
    assertThat(findUser.getId()).isSameAs(user.getId());
    assertThat(findUser.getEmail()).isEqualTo(user.getEmail());
  }

  @Test
  void findById() {
    //given
    UserEntity user = TestUser.getBuilder().build();
    //when
    UserEntity findUser = userRepository.findById(user.getId());
    //then
    assertThat(findUser.getId()).isSameAs(user.getId());
  }

  @Test
  @Transactional
  void remove() {
    //given
    UserEntity userEntity = TestUser.getBuilder().build();
    //when
    userRepository.delete(userEntity.getId());
    //then
    then(commonCheckService).should(times(1)).shouldBeMatch(anyInt(), anyInt());
  }

  @Test
  @Transactional
  void save() {
    //given
    UserSaveDto userSaveDto = UserSaveDto.builder()
        .email("abc@google.com")
        .password("q1w2e3")
        .name("abcUser")
        .gender(Gender.FEMALE)
        .build();
    //when
    userRepository.save(userSaveDto);
    //then
    then(commonCheckService).should(times(1)).shouldBeMatch(1, 1);
  }
}

