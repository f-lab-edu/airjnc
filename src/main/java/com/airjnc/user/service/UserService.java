package com.airjnc.user.service;

import com.airjnc.common.properties.SessionTtlProperties;
import com.airjnc.common.service.CommonUtilService;
import com.airjnc.common.service.RedisService;
import com.airjnc.common.util.BCryptHashEncrypter;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserModelMapper userModelMapper;

  private final UserRepository userRepository;

  private final UserCheckService userCheckService;

  private final NcpMailerService ncpMailerService;

  private final CommonUtilService commonUtilService;

  private final RedisService redisService;

  private final SessionTtlProperties sessionTtlProperties;

  public UserDTO create(CreateDTO createDTO) {
    userCheckService.emailShouldNotBeDuplicated(createDTO.getEmail());
    createDTO.changePasswordToHash();
    UserEntity userEntity = userRepository.save(createDTO);
    return userModelMapper.userEntityToUserDTO(userEntity);
  }

  public void remove(Long id) {
    userRepository.remove(id);
  }

  public String findEmail(FindEmailDTO findEmailDTO) {
    return userRepository.getEmail(findEmailDTO);
  }

  public void resetPasswordViaEmail(ResetPasswordCodeViaEmailDTO resetPasswordCodeViaEmailDTO) {
    UserEntity user = userRepository.findByEmail(resetPasswordCodeViaEmailDTO.getEmail());
    // 명령-질의 분리 원칙를 지키기 위해 `resetPasswordViaPhone` 에서 중복코드가 있음에도 불구하고, 하나로 합치지 않았습니다.
    String code = commonUtilService.generateCode();
    redisService.store(code, user.getEmail(), sessionTtlProperties.getResetPasswordCode());
    //
    ncpMailerService.send(
        NcpMailerSendDTO.builder()
            .email(resetPasswordCodeViaEmailDTO.getEmail())
            .name(user.getName())
            .code(code)
            .build()
    );
  }

  public void resetPasswordViaPhone(ResetPasswordCodeViaPhoneDTO resetPasswordCodeViaPhoneDTO) {
    UserEntity user = userRepository.findByPhoneNumber(resetPasswordCodeViaPhoneDTO.getPhoneNumber());
    String code = commonUtilService.generateCode();
    redisService.store(code, user.getEmail(), sessionTtlProperties.getResetPasswordCode());
    // TODO: send sms
  }

  public void resetPassword(ResetPasswordDTO resetPasswordDTO) {
    String email = redisService.get(resetPasswordDTO.getCode());
    redisService.remove(resetPasswordDTO.getCode());
    userRepository.updatePasswordByEmail(
        UpdatePasswordByEmailDTO.builder()
            .email(email)
            .password(BCryptHashEncrypter.encrypt(resetPasswordDTO.getPassword()))
            .build()
    );
  }
}
