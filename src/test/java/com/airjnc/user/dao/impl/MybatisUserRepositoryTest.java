package com.airjnc.user.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import com.airjnc.common.service.CommonValidateService;
import com.airjnc.user.dao.UserMapper;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.Gender;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UserWhereDto;
import com.airjnc.user.dto.UserWhereDto.UserStatus;
import com.testutil.annotation.MybatisTest;
import com.testutil.testdata.TestUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@MybatisTest
class MybatisUserRepositoryTest {

  UserRepository userRepository;

  @Autowired
  UserMapper userMapper;

  @Mock
  CommonValidateService commonValidateService;

  UserEntity testUser;

  @BeforeEach
  void beforeEach() {
    userRepository = new MybatisUserRepository(userMapper, commonValidateService);
    testUser = TestUser.getBuilder().build();
  }

  @Test
  @Transactional
  void create() {
    //given
    UserEntity newUserEntity = UserEntity.builder()
        .email("abc@google.com").password("q1w2e3").name("abcUser").gender(Gender.FEMALE).build();
    //when
    UserEntity userEntity = userRepository.create(newUserEntity);
    //then
    UserEntity byEmail = userRepository.findByWhere(
        UserWhereDto.builder().email(newUserEntity.getEmail()).status(UserStatus.ALL).build());
    assertThat(byEmail.getName()).isEqualTo(newUserEntity.getName());
    then(commonValidateService).should().shouldBeMatch(1, 1);
    assertThat(userEntity.getId()).isNotNull();
  }

  @Test
  void exists() {
    //given
    UserWhereDto dto1 = UserWhereDto.builder().id(testUser.getId()).build();
    UserWhereDto dto2 = UserWhereDto.builder().id(testUser.getId() + 1L).build();
    //when
    boolean exists1 = userRepository.exists(dto1);
    boolean exists2 = userRepository.exists(dto2);
    //then
    assertThat(exists1).isTrue();
    assertThat(exists2).isFalse();
  }

  @Test
  void findById() {
    //when
    UserEntity findUser = userRepository.findById(testUser.getId(), UserStatus.ACTIVE);
    //then
    assertThat(findUser.getId()).isSameAs(testUser.getId());
  }

  @Test
  void findByWhere() {
    //when
    UserWhereDto userWhereDto = UserWhereDto.builder().id(testUser.getId()).build();
    UserEntity findUser = userRepository.findByWhere(userWhereDto);
    //then
    assertThat(findUser.getId()).isSameAs(testUser.getId());
  }

  @Test
  @Transactional
  void save() {
    //given
    String password = "q1w2e3r4t5!@#";
    UserEntity userEntity = userRepository.findById(TestUser.ID, UserStatus.ALL);
    userEntity.setPassword(password);
    userEntity.delete();
    //when
    userRepository.save(userEntity);
    //then
    then(commonValidateService).should().shouldBeMatch(1, 1);
  }
}
