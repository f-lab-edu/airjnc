package com.airjnc.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import com.airjnc.common.dao.RedisDao;
import com.airjnc.common.properties.SessionTtlProperties;
import com.airjnc.common.service.CommonUtilService;
import com.airjnc.common.service.HashService;
import com.airjnc.ncp.dto.NcpMailerSendDto;
import com.airjnc.ncp.service.NcpMailerService;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UserSaveDto;
import com.airjnc.user.dto.request.UserCreateReq;
import com.airjnc.user.dto.request.UserGetResetPwdCodeViaEmailReq;
import com.airjnc.user.dto.request.UserGetResetPwdCodeViaPhoneReq;
import com.airjnc.user.dto.request.UserInquiryEmailReq;
import com.airjnc.user.dto.request.UserResetPwdReq;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.util.UserModelMapper;
import com.testutil.annotation.UnitTest;
import com.testutil.fixture.CreateDTOFixture;
import com.testutil.fixture.UserDTOFixture;
import com.testutil.fixture.UserInquiryEmailReqDTOFixture;
import com.testutil.testdata.TestUser;
import java.time.Duration;
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
  CommonUtilService commonUtilService;

  @Mock
  RedisDao redisDao;

  @Mock
  NcpMailerService ncpMailerService;

  @Mock
  SessionTtlProperties sessionTtlProperties;

  @InjectMocks
  UserService userService;

  @Test()
  void create() {
    //given
    UserCreateReq userCreateReq = spy(CreateDTOFixture.getBuilder().build());
    String hash = "hash";
    given(hashService.encrypt(userCreateReq.getPassword())).willReturn(hash);
    UserSaveDto userSaveDTO = mock(UserSaveDto.class);
    given(userCreateReq.toSaveDTO(hash)).willReturn(userSaveDTO);
    UserEntity userEntity = TestUser.getBuilder().build();
    given(userRepository.save(any(UserSaveDto.class))).willReturn(userEntity);
    UserResp userResp = UserDTOFixture.getBuilder().build();
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
  void remove() {
    //given
    UserEntity userEntity = TestUser.getBuilder().build();
    //when
    userService.delete(userEntity.getId());
    //then
    then(userRepository).should(times(1)).delete(userEntity.getId());
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
  void resetPasswordViaEmail() {
    //given
    UserGetResetPwdCodeViaEmailReq userGetResetPwdCodeViaEmailReq = new UserGetResetPwdCodeViaEmailReq(TestUser.EMAIL);
    UserEntity user = TestUser.getBuilder().build();
    given(userRepository.findByEmail(userGetResetPwdCodeViaEmailReq.getEmail())).willReturn(user);
    String code = "123456";
    given(commonUtilService.generateCode()).willReturn(code);
    given(sessionTtlProperties.getResetPasswordCode()).willReturn(Duration.ofMinutes(1L));
    //when
    userService.getResetPwdCodeViaEmail(userGetResetPwdCodeViaEmailReq);
    //then
    then(userRepository).should(times(1)).findByEmail(userGetResetPwdCodeViaEmailReq.getEmail());
    then(commonUtilService).should(times(1)).generateCode();
    then(redisDao).should(times(1)).store(eq(code), eq(user.getEmail()), any(Duration.class));
    then(ncpMailerService).should(times(1)).send(any(NcpMailerSendDto.class));
  }

  @Test
  void resetPasswordViaPhone() {
    //given
    UserGetResetPwdCodeViaPhoneReq userGetResetPwdCodeViaPhoneReq = new UserGetResetPwdCodeViaPhoneReq(
        TestUser.PHONE_NUMBER);
    UserEntity user = TestUser.getBuilder().build();
    given(userRepository.findByPhoneNumber(userGetResetPwdCodeViaPhoneReq.getPhone())).willReturn(user);
    String code = "123456";
    given(commonUtilService.generateCode()).willReturn(code);
    given(sessionTtlProperties.getResetPasswordCode()).willReturn(Duration.ofMinutes(1L));
    //when
    userService.getResetPwdCodeViaPhone(userGetResetPwdCodeViaPhoneReq);
    //then
    then(userRepository).should(times(1)).findByPhoneNumber(userGetResetPwdCodeViaPhoneReq.getPhone());
    then(commonUtilService).should(times(1)).generateCode();
    then(redisDao).should(times(1)).store(eq(code), eq(user.getEmail()), any(Duration.class));
  }
}

