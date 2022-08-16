package com.airjnc.user.service;

import com.airjnc.common.service.CommonHashService;
import com.airjnc.common.service.CommonValidateService;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UserWhereDto;
import com.airjnc.user.dto.UserWhereDto.UserStatus;
import com.airjnc.user.dto.request.UserCreateReq;
import com.airjnc.user.dto.request.UserUpdateMyEmailReq;
import com.airjnc.user.dto.request.UserUpdateMyInfoReq;
import com.airjnc.user.dto.request.UserUpdateMyPasswordReq;
import com.airjnc.user.dto.request.UserUpdatePwdReq;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.util.UserModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/*
 1. Service 와 DAO[Repository]는 1:1 매핑시킬 것.
 2. Service 는 아래에 나열된 Service외에 다른 Service를 의존하지 않을 것
    * `CommonXXXService`
    * 기능을 수행하기 위한 유효성 검사 서비스. 아래의 서비스 같은 경우엔 `UserCheckService`
 3. 단 하나의 역할만 수행할 것. `UserService` 같은 경우엔, `User` 와 관련된 기능만 존재할 것
    * 상태 저장, 제거 기능은 `User` 의 기능과 완전히 상반된 기능이므로, 해당 기능은 `UserService` 에서 진행하지 않는다.
 */
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserModelMapper userModelMapper;

  private final UserRepository userRepository;

  private final CommonHashService commonHashService;

  private final CommonValidateService commonValidateService;

  private final UserValidateService userValidateService;

  public UserResp create(UserCreateReq userCreateReq) {
    userValidateService.emailShouldNotBeDuplicated(userCreateReq.getEmail());
    String hash = commonHashService.encrypt(userCreateReq.getPassword());
    UserEntity userEntity = userModelMapper.userCreateReqToUserEntity(userCreateReq);
    userEntity.setPassword(hash);
    userRepository.create(userEntity);
    return userModelMapper.userEntityToUserResp(userEntity);
  }

  public void delete(Long userId) {
    UserEntity userEntity = userRepository.findById(userId, UserStatus.ACTIVE);
    userEntity.delete();
    userRepository.save(userEntity);
  }

  public UserResp getUserByEmailAndPassword(String email, String password) {
    UserWhereDto userWhereDto = UserWhereDto.builder().email(email).build();
    UserEntity userEntity = userRepository.findByWhere(userWhereDto);
    userValidateService.plainAndHashShouldMatch(password, userEntity.getPassword());
    return userModelMapper.userEntityToUserResp(userEntity);
  }

  public UserResp getUserById(Long userId, UserStatus userStatus) {
    UserEntity userEntity = userRepository.findById(userId, userStatus);
    return userModelMapper.userEntityToUserResp(userEntity);
  }

  public UserResp getUserByWhere(UserWhereDto userWhereDto) {
    UserEntity userEntity = userRepository.findByWhere(userWhereDto);
    return userModelMapper.userEntityToUserResp(userEntity);
  }

  public void restore(Long userId) {
    UserEntity userEntity = userRepository.findById(userId, UserStatus.DELETED);
    userEntity.restore();
    userRepository.save(userEntity);
  }

  public UserResp update(Long userId, UserUpdateMyInfoReq req) {
    UserEntity userEntity = userRepository.findById(userId, UserStatus.ACTIVE);
    userEntity.update(req);
    userRepository.save(userEntity);
    return userModelMapper.userEntityToUserResp(userEntity);
  }

  public UserResp updateMyEmail(Long userId, UserUpdateMyEmailReq userUpdateMyEmailReq) {
    UserEntity userEntity = userRepository.findById(userId, UserStatus.ACTIVE);
    commonValidateService.verifyCertificationCode(userEntity.getEmail(), userUpdateMyEmailReq.getCode());
    userEntity.setEmail(userUpdateMyEmailReq.getNewEmail());
    userRepository.save(userEntity);
    return userModelMapper.userEntityToUserResp(userEntity);
  }

  public UserResp updateMyPassword(Long userId, UserUpdateMyPasswordReq userUpdateMyPasswordReq) {
    UserEntity userEntity = userRepository.findById(userId, UserStatus.ACTIVE);
    userValidateService.plainAndHashShouldMatch(userUpdateMyPasswordReq.getPassword(), userEntity.getPassword());
    String newHash = commonHashService.encrypt(userUpdateMyPasswordReq.getNewPassword());
    userEntity.setPassword(newHash);
    userRepository.save(userEntity);
    return userModelMapper.userEntityToUserResp(userEntity);
  }

  public void updatePassword(UserUpdatePwdReq userUpdatePwdReq) {
    commonValidateService.verifyCertificationCode(userUpdatePwdReq.getEmail(), userUpdatePwdReq.getCertificationCode());
    UserWhereDto userWhereDto = UserWhereDto.builder().email(userUpdatePwdReq.getEmail()).status(UserStatus.ALL)
        .build();
    UserEntity userEntity = userRepository.findByWhere(userWhereDto);
    String hash = commonHashService.encrypt(userUpdatePwdReq.getPassword());
    userEntity.setPassword(hash);
    userRepository.save(userEntity);
  }
}
