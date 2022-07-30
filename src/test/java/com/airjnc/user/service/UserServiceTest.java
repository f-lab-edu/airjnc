package com.airjnc.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.spy;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.willDoNothing;
import com.airjnc.common.properties.SessionTtlProperties;
import com.airjnc.common.service.CommonUtilService;
import com.airjnc.common.service.RedisService;
import com.airjnc.ncp.dto.NcpMailerSendDTO;
import com.airjnc.ncp.service.NcpMailerService;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UpdatePasswordByEmailDTO;
import com.airjnc.user.dto.request.CreateDTO;
import com.airjnc.user.dto.request.FindEmailDTO;
import com.airjnc.user.dto.request.ResetPasswordCodeViaEmailDTO;
import com.airjnc.user.dto.request.ResetPasswordCodeViaPhoneDTO;
import com.airjnc.user.dto.request.ResetPasswordDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.util.UserModelMapper;
import com.testutil.annotation.UnitTest;
import com.testutil.fixture.CreateDTOFixture;
import com.testutil.fixture.FindEmailDTOFixture;
import com.testutil.fixture.UserDTOFixture;
import com.testutil.fixture.UserEntityFixture;
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
    CreateDTO createDTO = spy(CreateDTOFixture.getBuilder().build());
    UserEntity userEntity = UserEntityFixture.getBuilder().build();
    UserDTO userDTO = UserDTOFixture.getBuilder().build();
    willDoNothing().given(createDTO).changePasswordToHash();
    given(userRepository.save(createDTO)).willReturn(userEntity);
    given(userModelMapper.userEntityToUserDTO(userEntity)).willReturn(userDTO);
    //when
    UserDTO result = userService.create(createDTO);
    //then
    then(userCheckService).should(times(1)).emailShouldNotBeDuplicated(createDTO.getEmail());
    then(createDTO).should(times(1)).changePasswordToHash();
    then(userRepository).should(times(1)).save(createDTO);
    then(userModelMapper).should(times(1)).userEntityToUserDTO(userEntity);
    assertThat(result.getId()).isEqualTo(userDTO.getId());
  }

  @Test
  void remove() {
    //given
    UserEntity userEntity = UserEntityFixture.getBuilder().build();
    //when
    userService.remove(userEntity.getId());
    //then
    then(userRepository).should(times(1)).remove(userEntity.getId());
  }

  @Test
  void findEmail() {
    //given
    FindEmailDTO findEmailDTO = FindEmailDTOFixture.getBuilder().build();
    //when
    userService.findEmail(findEmailDTO);
    //then
    then(userRepository).should(times(1)).getEmail(findEmailDTO);
  }

  @Test
  void resetPasswordViaEmail() {
    //given
    ResetPasswordCodeViaEmailDTO resetPasswordCodeViaEmailDTO = new ResetPasswordCodeViaEmailDTO(UserEntityFixture.EMAIL);
    UserEntity user = UserEntityFixture.getBuilder().build();
    given(userRepository.findByEmail(resetPasswordCodeViaEmailDTO.getEmail())).willReturn(user);
    String code = "123456";
    given(commonUtilService.generateCode()).willReturn(code);
    given(sessionTtlProperties.getResetPasswordCode()).willReturn(Duration.ofMinutes(1L));
    //when
    userService.resetPasswordViaEmail(resetPasswordCodeViaEmailDTO);
    //then
    then(userRepository).should(times(1)).findByEmail(resetPasswordCodeViaEmailDTO.getEmail());
    then(commonUtilService).should(times(1)).generateCode();
    then(redisService).should(times(1)).store(eq(code), eq(user.getEmail()), any(Duration.class));
    then(ncpMailerService).should(times(1)).send(any(NcpMailerSendDTO.class));
  }

  @Test
  void resetPasswordViaPhone() {
    //given
    ResetPasswordCodeViaPhoneDTO resetPasswordCodeViaPhoneDTO = new ResetPasswordCodeViaPhoneDTO(UserEntityFixture.PHONE_NUMBER);
    UserEntity user = UserEntityFixture.getBuilder().build();
    given(userRepository.findByPhoneNumber(resetPasswordCodeViaPhoneDTO.getPhoneNumber())).willReturn(user);
    String code = "123456";
    given(commonUtilService.generateCode()).willReturn(code);
    given(sessionTtlProperties.getResetPasswordCode()).willReturn(Duration.ofMinutes(1L));
    //when
    userService.resetPasswordViaPhone(resetPasswordCodeViaPhoneDTO);
    //then
    then(userRepository).should(times(1)).findByPhoneNumber(resetPasswordCodeViaPhoneDTO.getPhoneNumber());
    then(commonUtilService).should(times(1)).generateCode();
    then(redisService).should(times(1)).store(eq(code), eq(user.getEmail()), any(Duration.class));
    ;
  }

  @Test
  void resetPassword() {
    //given
    ResetPasswordDTO resetPasswordDTO = ResetPasswordDTO.builder()
        .password("123456")
        .passwordConfirm("123456")
        .code("code")
        .build();
    //when
    userService.resetPassword(resetPasswordDTO);
    //then
    then(redisService).should(times(1)).get(resetPasswordDTO.getCode());
    then(redisService).should(times(1)).remove(resetPasswordDTO.getCode());
    then(userRepository).should(times(1)).updatePasswordByEmail(any(UpdatePasswordByEmailDTO.class));
  }
}

