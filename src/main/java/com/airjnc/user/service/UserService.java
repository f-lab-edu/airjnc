package com.airjnc.user.service;

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
import com.airjnc.user.dto.UserUpdatePwdByEmailDTO;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.util.UserModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final HashService hashService;

  private final UserModelMapper userModelMapper;

  private final UserRepository userRepository;

  private final UserCheckService userCheckService;

  private final NcpMailerService ncpMailerService;

  private final CommonUtilService commonUtilService;

  private final RedisService redisService;

  private final SessionTtlProperties sessionTtlProperties;

  public UserDTO create(UserCreateDTO userCreateDTO) {
    userCheckService.emailShouldNotBeDuplicated(userCreateDTO.getEmail());
    String hash = hashService.encrypt(userCreateDTO.getPassword());
    UserEntity userEntity = userRepository.save(userCreateDTO.toSaveDTO(hash));
    return userModelMapper.userEntityToUserDTO(userEntity);
  }

  public void remove(Long id) {
    userRepository.remove(id);
  }

  public String findEmail(UserFindEmailDTO userFindEmailDTO) {
    return userRepository.getEmail(userFindEmailDTO);
  }

  public void resetPasswordViaEmail(UserResetPwdCodeViaEmailDTO userResetPwdCodeViaEmailDTO) {
    UserEntity user = userRepository.findByEmail(userResetPwdCodeViaEmailDTO.getEmail());
    // 명령-질의 분리 원칙를 지키기 위해 `resetPasswordViaPhone` 에서 중복코드가 있음에도 불구하고, 하나로 합치지 않았습니다.
    String code = commonUtilService.generateCode();
    redisService.store(code, user.getEmail(), sessionTtlProperties.getResetPasswordCode());
    //
    ncpMailerService.send(
        NcpMailerSendDTO.builder()
            .email(userResetPwdCodeViaEmailDTO.getEmail())
            .name(user.getName())
            .code(code)
            .build()
    );
  }

  public void resetPasswordViaPhone(UserResetPwdCodeViaPhoneDTO userResetPwdCodeViaPhoneDTO) {
    UserEntity user = userRepository.findByPhoneNumber(userResetPwdCodeViaPhoneDTO.getPhoneNumber());
    String code = commonUtilService.generateCode();
    redisService.store(code, user.getEmail(), sessionTtlProperties.getResetPasswordCode());
    // TODO: send sms
  }

  public void resetPassword(UserResetPwdDTO userResetPwdDTO) {
    String email = redisService.get(userResetPwdDTO.getCode());
    redisService.remove(userResetPwdDTO.getCode());
    String hash = hashService.encrypt(userResetPwdDTO.getPassword());
    userRepository.updatePasswordByEmail(
        UserUpdatePwdByEmailDTO.builder()
            .email(email)
            .password(hash)
            .build()
    );
  }
}
