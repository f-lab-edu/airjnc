package com.airjnc.user.service;

import com.airjnc.common.properties.SessionTtlProperties;
import com.airjnc.common.service.CommonUtilService;
import com.airjnc.common.service.HashService;
import com.airjnc.common.service.RedisService;
import com.airjnc.ncp.dto.NcpMailerSendDto;
import com.airjnc.ncp.service.NcpMailerService;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.UserCreateReq;
import com.airjnc.user.dto.request.UserInquiryEmailReq;
import com.airjnc.user.dto.request.UserResetPwdCodeViaEmailReq;
import com.airjnc.user.dto.request.UserResetPwdCodeViaPhoneReq;
import com.airjnc.user.dto.request.UserResetPwdReq;
import com.airjnc.user.dto.response.UserInquiryEmailResp;
import com.airjnc.user.dto.response.UserResp;
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

  public UserResp create(UserCreateReq userCreateReq) {
    userCheckService.emailShouldNotBeDuplicated(userCreateReq.getEmail());
    String hash = hashService.encrypt(userCreateReq.getPassword());
    UserEntity userEntity = userRepository.save(userCreateReq.toSaveDTO(hash));
    return userModelMapper.userEntityToUserDTO(userEntity);
  }

  public UserInquiryEmailResp inquiryEmail(UserInquiryEmailReq userInquiryEmailReq) {
    return userRepository.findEmailByNameAndBirthDate(
        userInquiryEmailReq.getName(),
        userInquiryEmailReq.getBirthDate()
    );
  }

  public void remove(Long id) {
    userRepository.remove(id);
  }

  public void resetPassword(UserResetPwdReq userResetPwdReq) {
    String email = redisService.get(userResetPwdReq.getCode());
    redisService.remove(userResetPwdReq.getCode());
    String hash = hashService.encrypt(userResetPwdReq.getPassword());
    userRepository.updatePasswordByEmail(email, hash);
  }

  public void resetPasswordViaEmail(UserResetPwdCodeViaEmailReq userResetPwdCodeViaEmailReq) {
    UserEntity user = userRepository.findByEmail(userResetPwdCodeViaEmailReq.getEmail());
    // 명령-질의 분리 원칙를 지키기 위해 `resetPasswordViaPhone` 에서 중복코드가 있음에도 불구하고, 하나로 합치지 않았습니다.
    String code = commonUtilService.generateCode();
    redisService.store(code, user.getEmail(), sessionTtlProperties.getResetPasswordCode());
    //
    ncpMailerService.send(
        NcpMailerSendDto.builder()
            .email(userResetPwdCodeViaEmailReq.getEmail())
            .name(user.getName())
            .code(code)
            .build()
    );
  }

  public void resetPasswordViaPhone(UserResetPwdCodeViaPhoneReq userResetPwdCodeViaPhoneReq) {
    UserEntity user = userRepository.findByPhoneNumber(userResetPwdCodeViaPhoneReq.getPhoneNumber());
    String code = commonUtilService.generateCode();
    redisService.store(code, user.getEmail(), sessionTtlProperties.getResetPasswordCode());
    // TODO: send sms
  }
}
