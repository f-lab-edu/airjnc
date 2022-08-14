package com.airjnc.user.service;

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
import com.airjnc.user.dto.response.UserInquiryEmailResp;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.util.UserModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssembleUserService {

  private final UserModelMapper userModelMapper;

  private final UserRepository userRepository;

  private final StateService stateService;

  private final HashService hashService;

  private final UserCheckService userCheckService;

  private final CommonCheckService commonCheckService;

  public UserResp create(UserCreateReq userCreateReq) {
    userCheckService.emailShouldNotBeDuplicated(userCreateReq.getEmail());
    String hash = hashService.encrypt(userCreateReq.getPassword());
    UserEntity userEntity = userModelMapper.userCreateReqToUserEntity(userCreateReq);
    userEntity.setPassword(hash);
    userRepository.create(userEntity);
    stateService.create(SessionKey.USER, userEntity.getId());
    return userModelMapper.userEntityToUserResp(userEntity);
  }

  public void delete(Long userId) {
    UserEntity userEntity = userRepository.findById(userId, UserStatus.ACTIVE);
    userEntity.delete();
    userRepository.save(userEntity);
    stateService.delete(SessionKey.USER);
  }

  public UserResp getUserById(Long userId, UserStatus userStatus) {
    UserEntity userEntity = userRepository.findById(userId, userStatus);
    return userModelMapper.userEntityToUserResp(userEntity);
  }

  public UserResp getUserByWhere(UserWhereDto userWhereDto) {
    UserEntity userEntity = userRepository.findByWhere(userWhereDto);
    return userModelMapper.userEntityToUserResp(userEntity);
  }

  public UserInquiryEmailResp inquiryEmail(UserInquiryEmailReq req) {
    UserWhereDto userWhereDto = UserWhereDto.builder()
        .name(req.getName()).birthDate(req.getBirthDate()).status(UserStatus.ALL).build();
    UserEntity userEntity = userRepository.findByWhere(userWhereDto);
    return userModelMapper.userEntityToUserInquiryEmailResp(userEntity);
  }

  public void resetPassword(UserResetPwdReq userResetPwdReq) {
    commonCheckService.verifyCode(userResetPwdReq.getEmail(), userResetPwdReq.getCode());
    UserWhereDto userWhereDto = UserWhereDto.builder().email(userResetPwdReq.getEmail()).status(UserStatus.ALL).build();
    UserEntity userEntity = userRepository.findByWhere(userWhereDto);
    String hash = hashService.encrypt(userResetPwdReq.getPassword());
    userEntity.setPassword(hash);
    userRepository.save(userEntity);
  }

  public void restore(Long userId) {
    UserEntity userEntity = userRepository.findById(userId, UserStatus.DELETED);
    userEntity.restore();
    userRepository.save(userEntity);
  }
}
