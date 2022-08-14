package com.airjnc.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import static org.mockito.Mockito.spy;
import com.airjnc.common.dao.RedisDao;
import com.airjnc.common.service.CommonCheckService;
import com.airjnc.common.service.HashService;
import com.airjnc.common.service.StateService;
import com.airjnc.common.util.enumerate.SessionKey;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UserWhereDto;
import com.airjnc.user.dto.UserWhereDto.UserStatus;
import com.airjnc.user.dto.request.UserCreateReq;
import com.airjnc.user.dto.request.UserInquiryEmailReq;
import com.airjnc.user.dto.request.UserResetPwdReq;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.util.UserModelMapper;
import com.testutil.annotation.UnitTest;
import com.testutil.fixture.UserCreateReqFixture;
import com.testutil.fixture.UserInquiryEmailReqDTOFixture;
import com.testutil.fixture.UserRespFixture;
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
  HashService hashService;

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

  @Mock
  StateService stateService;

  @InjectMocks
  UserService userService;

  @Test()
  void create() {
    //given
    UserCreateReq userCreateReq = spy(UserCreateReqFixture.getBuilder().build());
    String hash = "hash";
    UserEntity userEntity = TestUser.getBuilder().build();
    UserResp userResp = UserRespFixture.getBuilder().build();
    given(hashService.encrypt(userCreateReq.getPassword())).willReturn(hash);
    given(userModelMapper.userCreateReqToUserEntity(userCreateReq)).willReturn(userEntity);
    given(userModelMapper.userEntityToUserResp(userEntity)).willReturn(userResp);
    //when
    UserResp result = userService.create(userCreateReq);
    //then
    then(userCheckService).should(times(1)).emailShouldNotBeDuplicated(userCreateReq.getEmail());
    then(hashService).should(times(1)).encrypt(userCreateReq.getPassword());
    then(userModelMapper).should(times(1)).userEntityToUserResp(any(UserEntity.class));
    assertThat(userEntity.getPassword()).isEqualTo(hash);
    then(userRepository).should(times(1)).create(userEntity);
    then(stateService).should(times(1)).create(SessionKey.USER, userEntity.getId());
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
    then(stateService).should(times(1)).delete(SessionKey.USER);
  }

  @Test
  void inquiryEmail() {
    //given
    UserInquiryEmailReq userInquiryEmailReq = UserInquiryEmailReqDTOFixture.getBuilder().build();
    UserEntity userEntity = TestUser.getBuilder().build();
    given(userRepository.findByWhere(any(UserWhereDto.class))).willReturn(userEntity);
    //when
    userService.inquiryEmail(userInquiryEmailReq);
    //then
    then(userModelMapper).should(times(1)).userEntityToUserInquiryEmailResp(userEntity);
    then(userRepository).should(times(1)).findByWhere(any(UserWhereDto.class));
  }

  @Test
  void resetPassword() {
    //given
    UserResetPwdReq userResetPwdReq = UserResetPwdReq.builder()
        .email("test@naver.com").password("123456").code("code").build();
    String hash = "hash";
    UserEntity userEntity = TestUser.getBuilder().build();
    given(userRepository.findByWhere(any(UserWhereDto.class))).willReturn(userEntity);
    given(hashService.encrypt(userResetPwdReq.getPassword())).willReturn(hash);
    //when
    userService.resetPassword(userResetPwdReq);
    //then
    then(commonCheckService).should(times(1)).verifyCode(userResetPwdReq.getEmail(), userResetPwdReq.getCode());
    then(userRepository).should(times(1)).findByWhere(any(UserWhereDto.class));
    then(hashService).should(times(1)).encrypt(userResetPwdReq.getPassword());
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

