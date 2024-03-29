package com.airjnc.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.spy;

import com.airjnc.common.service.CommonHashService;
import com.airjnc.common.service.CommonValidateService;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.Gender;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UserWhereDto;
import com.airjnc.user.dto.UserWhereDto.UserStatus;
import com.airjnc.user.dto.request.UserCreateReq;
import com.airjnc.user.dto.request.UserUpdateMyEmailReq;
import com.airjnc.user.dto.request.UserUpdateMyInfoReq;
import com.airjnc.user.dto.request.UserUpdateMyPasswordReq;
import com.airjnc.user.dto.request.UserUpdatePwdReq;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.util.UserModelMapper;
import com.testutil.annotation.UnitTest;
import com.testutil.fixture.user.UserCreateReqFixture;
import com.testutil.fixture.user.UserRespFixture;
import com.testutil.testdata.TestUser;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@UnitTest
class UserServiceTest {

  @Mock
  CommonHashService commonHashService;

  @Mock
  UserRepository userRepository;

  @Mock
  UserModelMapper userModelMapper;

  @Mock
  UserValidateService userValidateService;

  @Mock
  CommonValidateService commonValidateService;

  @InjectMocks
  UserService userService;

  @Test()
  void create() {
    //given
    UserCreateReq userCreateReq = spy(UserCreateReqFixture.getBuilder().build());
    String hash = "hash";
    UserEntity userEntity = TestUser.getBuilder().build();
    UserResp userResp = UserRespFixture.getBuilder().build();
    given(commonHashService.encrypt(userCreateReq.getPassword())).willReturn(hash);
    given(userModelMapper.userCreateReqToUserEntity(userCreateReq)).willReturn(userEntity);
    given(userModelMapper.userEntityToUserResp(userEntity)).willReturn(userResp);
    //when
    UserResp result = userService.create(userCreateReq);
    //then
    then(userValidateService).should().emailShouldNotBeDuplicated(userCreateReq.getEmail());
    then(commonHashService).should().encrypt(userCreateReq.getPassword());
    then(userModelMapper).should().userEntityToUserResp(any(UserEntity.class));
    assertThat(userEntity.getPassword()).isEqualTo(hash);
    then(userRepository).should().create(userEntity);
    assertThat(result.getId()).isEqualTo(userResp.getId());
  }

  @Test
  void delete() {
    //given
    UserEntity userEntity = TestUser.getBuilder().build();
    given(userRepository.findById(userEntity.getId(), UserStatus.ACTIVE)).willReturn(userEntity);
    //when
    userService.delete(userEntity.getId());
    //then
    assertThat(userEntity.isDeleted()).isTrue();
    then(userRepository).should().save(userEntity);
  }

  @Test
  void getUserByAuth() {
    //given
    String email = TestUser.EMAIL;
    String password = TestUser.PASSWORD;
    UserEntity userEntity = TestUser.getBuilder().build();
    UserResp userResp = UserResp.builder().build();

    given(userRepository.findByWhere(any(UserWhereDto.class))).willReturn(userEntity);
    given(userModelMapper.userEntityToUserResp(userEntity)).willReturn(userResp);
    //when
    UserResp result = userService.getUserByEmailAndPassword(email, password);
    //then
    then(userRepository).should().findByWhere(any(UserWhereDto.class));
    then(userValidateService).should().plainAndHashShouldMatch(password, userEntity.getPassword());
    then(userModelMapper).should().userEntityToUserResp(userEntity);
    assertThat(result).isSameAs(userResp);
  }

  @Test
  void resetPassword() {
    //given
    UserUpdatePwdReq userUpdatePwdReq = UserUpdatePwdReq.builder()
        .email("test@naver.com").password("123456").certificationCode("code").build();
    String hash = "hash";
    UserEntity userEntity = TestUser.getBuilder().build();
    given(userRepository.findByWhere(any(UserWhereDto.class))).willReturn(userEntity);
    given(commonHashService.encrypt(userUpdatePwdReq.getPassword())).willReturn(hash);
    //when
    userService.updatePassword(userUpdatePwdReq);
    //then
    then(commonValidateService).should()
        .verifyCertificationCode(userUpdatePwdReq.getEmail(), userUpdatePwdReq.getCertificationCode());
    then(userRepository).should().findByWhere(any(UserWhereDto.class));
    then(commonHashService).should().encrypt(userUpdatePwdReq.getPassword());
    assertThat(userEntity.getPassword()).isEqualTo(hash);
    then(userRepository).should().save(userEntity);
  }

  @Test
  void restore() {
    //given
    UserEntity userEntity = TestUser.getBuilder().build();
    UserStatus deleted = UserStatus.DELETED;
    given(userRepository.findById(userEntity.getId(), deleted)).willReturn(userEntity);
    //when
    userService.restore(userEntity.getId());
    //then
    then(userRepository).should().findById(userEntity.getId(), deleted);
    assertThat(userEntity.isDeleted()).isFalse();
    then(userRepository).should().save(userEntity);
  }

  @Test
  void updateMyEmail() {
    //given
    UserUpdateMyEmailReq userUpdateMyEmailReq = UserUpdateMyEmailReq.builder()
        .newEmail("new@google.com").code("123456").build();
    UserEntity userEntity = TestUser.getBuilder().build();
    given(userRepository.findById(TestUser.ID, UserStatus.ACTIVE)).willReturn(userEntity);
    //when
    userService.updateMyEmail(TestUser.ID, userUpdateMyEmailReq);
    //then
    then(userRepository).should().findById(TestUser.ID, UserStatus.ACTIVE);
    then(commonValidateService).should().verifyCertificationCode(anyString(), eq(userUpdateMyEmailReq.getCode()));
    assertThat(userEntity.getEmail()).isEqualTo(userUpdateMyEmailReq.getNewEmail());
    then(userRepository).should().save(userEntity);
    then(userModelMapper).should().userEntityToUserResp(userEntity);
  }

  @Test
  void updateMyPassword() {
    //given
    Long userId = TestUser.ID;
    UserUpdateMyPasswordReq userUpdateMyPasswordReq = UserUpdateMyPasswordReq.builder()
        .password(TestUser.PASSWORD).newPassword("newPassword").build();
    UserEntity userEntity = TestUser.getBuilder().build();
    given(userRepository.findById(userId, UserStatus.ACTIVE)).willReturn(userEntity);
    //when
    userService.updateMyPassword(userId, userUpdateMyPasswordReq);
    //then
    then(userRepository).should().findById(userId, UserStatus.ACTIVE);
    then(userValidateService).should()
        .plainAndHashShouldMatch(eq(userUpdateMyPasswordReq.getPassword()), anyString());
    then(commonHashService).should().encrypt(userUpdateMyPasswordReq.getNewPassword());
    then(userRepository).should().save(userEntity);
    then(userModelMapper).should().userEntityToUserResp(userEntity);
  }

  @Nested
  class Update {

    UserEntity userEntity;

    @BeforeEach
    void beforeEach() {
      userEntity = TestUser.getBuilder().build();
      given(userRepository.findById(userEntity.getId(), UserStatus.ACTIVE)).willReturn(userEntity);
    }

    void commonCheck() {
      then(userRepository).should().findById(userEntity.getId(), UserStatus.ACTIVE);
      then(userRepository).should().save(any(UserEntity.class));
      then(userModelMapper).should().userEntityToUserResp(any(UserEntity.class));
    }

    @Test
    void whenFieldOfReqIsNotNullThenFieldOfUserEntityIsUpdated() {
      //given
      UserUpdateMyInfoReq req = UserUpdateMyInfoReq.builder()
          .name("abc").gender(Gender.FEMALE).address("address").birthDate(LocalDate.now()).build();
      //when
      userService.update(userEntity.getId(), req);
      //then
      commonCheck();
      assertThat(userEntity.getName()).isEqualTo(req.getName());
      assertThat(userEntity.getGender()).isEqualTo(req.getGender());
      assertThat(userEntity.getAddress()).isEqualTo(req.getAddress());
      assertThat(userEntity.getBirthDate()).isEqualTo(req.getBirthDate());
    }

    @Test
    void whenFieldOfReqIsNullThenFieldOfUserEntityDoNotUpdated() {
      //given
      UserUpdateMyInfoReq req = UserUpdateMyInfoReq.builder().build();
      String name = userEntity.getName();
      Gender gender = userEntity.getGender();
      String address = userEntity.getAddress();
      LocalDate birthDate = userEntity.getBirthDate();
      //when
      userService.update(userEntity.getId(), req);
      //then
      commonCheck();
      assertThat(name).isEqualTo(userEntity.getName());
      assertThat(gender).isEqualTo(userEntity.getGender());
      assertThat(address).isEqualTo(userEntity.getAddress());
      assertThat(birthDate).isEqualTo(userEntity.getBirthDate());
    }
  }
}
