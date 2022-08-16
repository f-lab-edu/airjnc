package com.airjnc.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import static org.mockito.Mockito.spy;
import com.airjnc.common.dao.RedisDao;
import com.airjnc.common.service.CommonCheckService;
import com.airjnc.common.service.CommonHashService;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UserWhereDto;
import com.airjnc.user.dto.UserWhereDto.UserStatus;
import com.airjnc.user.dto.request.UserCreateReq;
import com.airjnc.user.dto.request.UserResetPwdReq;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.util.UserModelMapper;
import com.testutil.annotation.UnitTest;
import com.testutil.fixture.user.UserCreateReqFixture;
import com.testutil.fixture.user.UserRespFixture;
import com.testutil.testdata.TestUser;
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
  UserCheckService userCheckService;

  @Mock
  RedisDao redisDao;

  @Mock
  CommonCheckService commonCheckService;

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
    then(userCheckService).should(times(1)).emailShouldNotBeDuplicated(userCreateReq.getEmail());
    then(commonHashService).should(times(1)).encrypt(userCreateReq.getPassword());
    then(userModelMapper).should(times(1)).userEntityToUserResp(any(UserEntity.class));
    assertThat(userEntity.getPassword()).isEqualTo(hash);
    then(userRepository).should(times(1)).create(userEntity);
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
    then(userRepository).should(times(1)).save(userEntity);
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
    then(userRepository).should(times(1)).findByWhere(any(UserWhereDto.class));
    then(userCheckService).should(times(1)).passwordShouldBeMatch(password, userEntity.getPassword());
    then(userModelMapper).should(times(1)).userEntityToUserResp(userEntity);
    assertThat(result).isSameAs(userResp);
  }

  @Test
  void resetPassword() {
    //given
    UserResetPwdReq userResetPwdReq = UserResetPwdReq.builder()
        .email("test@naver.com").password("123456").certificationCode("code").build();
    String hash = "hash";
    UserEntity userEntity = TestUser.getBuilder().build();
    given(userRepository.findByWhere(any(UserWhereDto.class))).willReturn(userEntity);
    given(commonHashService.encrypt(userResetPwdReq.getPassword())).willReturn(hash);
    //when
    userService.resetPassword(userResetPwdReq);
    //then
    then(commonCheckService).should(times(1)).verifyCertificationCode(userResetPwdReq.getEmail(), userResetPwdReq.getCertificationCode());
    then(userRepository).should(times(1)).findByWhere(any(UserWhereDto.class));
    then(commonHashService).should(times(1)).encrypt(userResetPwdReq.getPassword());
    assertThat(userEntity.getPassword()).isEqualTo(hash);
    then(userRepository).should(times(1)).save(userEntity);
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
    then(userRepository).should(times(1)).findById(userEntity.getId(), deleted);
    assertThat(userEntity.isDeleted()).isFalse();
    then(userRepository).should(times(1)).save(userEntity);
  }
}
