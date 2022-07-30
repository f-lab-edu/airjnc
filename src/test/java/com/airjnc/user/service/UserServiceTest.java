package com.airjnc.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import com.airjnc.common.properties.SessionTtlProperties;
import com.airjnc.common.service.CommonUtilService;
import com.airjnc.common.service.HashService;
import com.airjnc.common.service.RedisService;
import com.airjnc.ncp.service.NcpMailerService;
import com.airjnc.ncp.dto.NcpMailerSendDTO;
import com.airjnc.user.dto.request.UserCreateDTO;
import com.airjnc.user.dto.request.UserFindEmailDTO;
import com.airjnc.user.dto.request.UserResetPwdCodeViaEmailDTO;
import com.airjnc.user.dto.request.UserResetPwdCodeViaPhoneDTO;
import com.airjnc.user.dto.request.UserResetPwdDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.dto.UserSaveDTO;
import com.airjnc.user.dto.UserUpdatePwdByEmailDTO;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.util.UserModelMapper;
import com.testutil.annotation.UnitTest;
import com.testutil.fixture.CreateDTOFixture;
import com.testutil.fixture.FindEmailDTOFixture;
import com.testutil.fixture.UserDTOFixture;
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
  RedisService redisService;

  @Mock
  NcpMailerService ncpMailerService;

  @Mock
  SessionTtlProperties sessionTtlProperties;

  @InjectMocks
  UserService userService;

  @Test()
  void create() {
    //given
    UserCreateDTO userCreateDTO = spy(CreateDTOFixture.getBuilder().build());
    String hash = "hash";
    given(hashService.encrypt(userCreateDTO.getPassword())).willReturn(hash);
    UserSaveDTO userSaveDTO = mock(UserSaveDTO.class);
    given(userCreateDTO.toSaveDTO(hash)).willReturn(userSaveDTO);
    UserEntity userEntity = TestUser.getBuilder().build();
    given(userRepository.save(any(UserSaveDTO.class))).willReturn(userEntity);
    UserDTO userDTO = UserDTOFixture.getBuilder().build();
    given(userModelMapper.userEntityToUserDTO(userEntity)).willReturn(userDTO);
    //when
    UserDTO result = userService.create(userCreateDTO);
    //then
    then(userCheckService).should(times(1)).emailShouldNotBeDuplicated(userCreateDTO.getEmail());
    then(hashService).should(times(1)).encrypt(userCreateDTO.getPassword());
    then(userCreateDTO).should(times(1)).toSaveDTO(hash);
    then(userRepository).should(times(1)).save(userSaveDTO);
    then(userModelMapper).should(times(1)).userEntityToUserDTO(any(UserEntity.class));
    assertThat(result.getId()).isEqualTo(userDTO.getId());
  }

  @Test
  void findEmail() {
    //given
    UserFindEmailDTO userFindEmailDTO = FindEmailDTOFixture.getBuilder().build();
    //when
    userService.findEmail(userFindEmailDTO);
    //then
    then(userRepository).should(times(1)).getEmail(userFindEmailDTO);
  }

  @Test
  void remove() {
    //given
    UserEntity userEntity = TestUser.getBuilder().build();
    //when
    userService.remove(userEntity.getId());
    //then
    then(userRepository).should(times(1)).remove(userEntity.getId());
  }

  @Test
  void resetPassword() {
    //given
    UserResetPwdDTO userResetPwdDTO = UserResetPwdDTO.builder()
        .password("123456")
        .passwordConfirm("123456")
        .code("code")
        .build();
    //when
    userService.resetPassword(userResetPwdDTO);
    //then
    then(redisService).should(times(1)).get(userResetPwdDTO.getCode());
    then(redisService).should(times(1)).remove(userResetPwdDTO.getCode());
    then(hashService).should(times(1)).encrypt(userResetPwdDTO.getPassword());
    then(userRepository).should(times(1)).updatePasswordByEmail(any(UserUpdatePwdByEmailDTO.class));
  }

  @Test
  void resetPasswordViaEmail() {
    //given
    UserResetPwdCodeViaEmailDTO userResetPwdCodeViaEmailDTO = new UserResetPwdCodeViaEmailDTO(TestUser.EMAIL);
    UserEntity user = TestUser.getBuilder().build();
    given(userRepository.findByEmail(userResetPwdCodeViaEmailDTO.getEmail())).willReturn(user);
    String code = "123456";
    given(commonUtilService.generateCode()).willReturn(code);
    given(sessionTtlProperties.getResetPasswordCode()).willReturn(Duration.ofMinutes(1L));
    //when
    userService.resetPasswordViaEmail(userResetPwdCodeViaEmailDTO);
    //then
    then(userRepository).should(times(1)).findByEmail(userResetPwdCodeViaEmailDTO.getEmail());
    then(commonUtilService).should(times(1)).generateCode();
    then(redisService).should(times(1)).store(eq(code), eq(user.getEmail()), any(Duration.class));
    then(ncpMailerService).should(times(1)).send(any(NcpMailerSendDTO.class));
  }

  @Test
  void resetPasswordViaPhone() {
    //given
    UserResetPwdCodeViaPhoneDTO userResetPwdCodeViaPhoneDTO = new UserResetPwdCodeViaPhoneDTO(TestUser.PHONE_NUMBER);
    UserEntity user = TestUser.getBuilder().build();
    given(userRepository.findByPhoneNumber(userResetPwdCodeViaPhoneDTO.getPhoneNumber())).willReturn(user);
    String code = "123456";
    given(commonUtilService.generateCode()).willReturn(code);
    given(sessionTtlProperties.getResetPasswordCode()).willReturn(Duration.ofMinutes(1L));
    //when
    userService.resetPasswordViaPhone(userResetPwdCodeViaPhoneDTO);
    //then
    then(userRepository).should(times(1)).findByPhoneNumber(userResetPwdCodeViaPhoneDTO.getPhoneNumber());
    then(commonUtilService).should(times(1)).generateCode();
    then(redisService).should(times(1)).store(eq(code), eq(user.getEmail()), any(Duration.class));
  }
}

