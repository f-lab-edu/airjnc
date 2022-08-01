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
import com.airjnc.user.dto.UserSaveDto;
import com.airjnc.user.dto.request.UserCreateReq;
import com.airjnc.user.dto.response.UserInquiryEmailResp;
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
        () -> userRepository.findByEmail(testUser.getEmail())
    );
    then(commonCheckService).should(times(1)).shouldBeMatch(anyInt(), anyInt());
  }

  @Test
  void findByEmail() {
    //when
    UserEntity findUser = userRepository.findByEmail(testUser.getEmail());
    //then
    assertThat(findUser.getId()).isSameAs(testUser.getId());
    assertThat(findUser.getEmail()).isEqualTo(testUser.getEmail());
  }

  @Test
  void findById() {
    //when
    UserEntity findUser = userRepository.findById(testUser.getId());
    //then
    assertThat(findUser.getId()).isSameAs(testUser.getId());
  }

  @Test
  void findByPhoneNumber() {
    //when
    UserEntity findUser = userRepository.findByPhoneNumber(testUser.getPhoneNumber());
    //then
    assertThat(findUser.getId()).isSameAs(testUser.getId());
  }

  @Test
  void findWithDeletedByNameAndBirthDate() {
    //when
    UserEntity findUser = userRepository.findWithDeletedByNameAndBirthDate(testUser.getName(), testUser.getBirthDate());
    //then
    assertThat(findUser.getEmail()).isEqualTo(testUser.getEmail());
  }

  @Test
  @Transactional
  void findOnlyDeletedById_givenDeletedThenSuccess() {
    userRepository.delete(testUser.getId());
    //when
    UserEntity findUser = userRepository.findOnlyDeletedById(testUser.getId());
    //then
    assertThat(testUser.getId()).isEqualTo(findUser.getId());
  }

  @Test
  void findOnlyDeletedById_givenNotDeletedThenThrowException() {
    //when
    Assertions.assertThrows(
        NotFoundException.class,
        () -> userRepository.findOnlyDeletedById(testUser.getId())
    );
  }

  @Test
  @Transactional
  void findWithDeletedByEmail_givenDeletedThenSuccess() {
    //given
    userMapper.delete(testUser.getId());
    //when
    UserEntity findUser = userRepository.findWithDeletedByEmail(testUser.getEmail());
    //then
    assertThat(testUser.getId()).isEqualTo(findUser.getId());
  }

  @Test
  void findWithDeletedByEmail_givenNotDeletedThenSuccess() {
    //when
    UserEntity findUser = userRepository.findWithDeletedByEmail(testUser.getEmail());
    //then
    assertThat(testUser.getId()).isEqualTo(findUser.getId());
  }

  @Test
  @Transactional
  void restore() {
    //given
    userMapper.delete(testUser.getId());
    UserEntity deletedUser = userRepository.findWithDeletedByEmail(testUser.getEmail());
    //when
    userRepository.restore(testUser.getId());
    UserEntity restoredUser = userRepository.findWithDeletedByEmail(testUser.getEmail());
    //then
    assertThat(deletedUser.isDeleted()).isTrue();
    assertThat(restoredUser.isDeleted()).isFalse();
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
    userRepository.save(userSaveDTO);
    //then
    UserEntity byEmail = userRepository.findByEmail(userSaveDTO.getEmail());
    assertThat(byEmail.getName()).isEqualTo(userSaveDTO.getName());
    then(commonCheckService).should(times(1)).shouldBeMatch(1, 1);
  }

  @Test
  @Transactional
  void updatePasswordByEmail() {
    //given
    String email = TestUser.EMAIL;
    String passwrod = "q1w2e3r4t5!@#";
    //when
    userRepository.updatePasswordByEmail(email, passwrod);
    //then
    then(commonCheckService).should(times(1)).shouldBeMatch(anyInt(), anyInt());
  }
}

