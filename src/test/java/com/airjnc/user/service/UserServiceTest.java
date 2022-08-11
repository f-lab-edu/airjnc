package com.airjnc.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import com.airjnc.common.dao.RedisDao;
import com.airjnc.common.service.HashService;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UserSaveDto;
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
  UserCheckService userCheckService;

  @Mock
  UserModelMapper userModelMapper;

  @Mock
  RedisDao redisDao;

  @InjectMocks
  UserService userService;

  @Test()
  void create() {
    //given
    UserCreateReq userCreateReq = spy(UserCreateReqFixture.getBuilder().build());
    String hash = "hash";
    UserSaveDto userSaveDTO = mock(UserSaveDto.class);
    UserEntity userEntity = TestUser.getBuilder().build();
    UserResp userResp = UserRespFixture.getBuilder().build();
    given(hashService.encrypt(userCreateReq.getPassword())).willReturn(hash);
    given(userCreateReq.toSaveDTO(hash)).willReturn(userSaveDTO);
    given(userRepository.save(any(UserSaveDto.class))).willReturn(userEntity);
    given(userModelMapper.userEntityToUserResp(userEntity)).willReturn(userResp);
    //when
    UserResp result = userService.create(userCreateReq);
    //then
    then(userCheckService).should(times(1)).emailShouldNotBeDuplicated(userCreateReq.getEmail());
    then(hashService).should(times(1)).encrypt(userCreateReq.getPassword());
    then(userCreateReq).should(times(1)).toSaveDTO(hash);
    then(userRepository).should(times(1)).save(userSaveDTO);
    then(userModelMapper).should(times(1)).userEntityToUserResp(any(UserEntity.class));
    assertThat(result.getId()).isEqualTo(userResp.getId());
  }

  @Test
  void delete() {
    //given
    UserEntity userEntity = TestUser.getBuilder().build();
    //when
    userService.delete(userEntity.getId());
    //then
    then(userRepository).should(times(1)).delete(userEntity.getId());
  }

  @Test
  void inquiryEmail() {
    //given
    UserInquiryEmailReq dto = UserInquiryEmailReqDTOFixture.getBuilder().build();
    UserEntity userEntity = TestUser.getBuilder().build();
    given(userRepository.findWithDeletedByNameAndBirthDate(dto.getName(), dto.getBirthDate())).willReturn(userEntity);
    //when
    userService.inquiryEmail(dto);
    //then
    then(userModelMapper).should(times(1)).userEntityToUserInquiryEmailResp(userEntity);
    then(userRepository).should(times(1)).findWithDeletedByNameAndBirthDate(dto.getName(), dto.getBirthDate());
  }

  @Test
  void resetPassword() {
    //given
    UserResetPwdReq userResetPwdReq = UserResetPwdReq.builder()
        .password("123456")
        .code("code")
        .build();
    String email = "test@google.com";
    String hash = "hash";
    given(redisDao.get(userResetPwdReq.getCode())).willReturn(email);
    given(hashService.encrypt(userResetPwdReq.getPassword())).willReturn(hash);
    //when
    userService.resetPassword(userResetPwdReq);
    //then
    then(redisDao).should(times(1)).get(userResetPwdReq.getCode());
    then(redisDao).should(times(1)).delete(userResetPwdReq.getCode());
    then(hashService).should(times(1)).encrypt(userResetPwdReq.getPassword());
    then(userRepository).should(times(1)).updatePasswordByEmail(email, hash);
  }

  @Test
  void restore() {
    //given
    UserEntity user = TestUser.getBuilder().build();
    given(userRepository.findOnlyDeletedById(user.getId())).willReturn(user);
    //when
    userService.restore(user.getId());
    //then
    then(userRepository).should(times(1)).findOnlyDeletedById(user.getId());
    then(userCheckService).should(times(1)).shouldBeDeleted(user);
    then(userRepository).should(times(1)).restore(user.getId());
  }
}

