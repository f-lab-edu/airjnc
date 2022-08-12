package com.airjnc.user.service;

import com.airjnc.common.dao.RedisDao;
import com.airjnc.common.service.CommonCheckService;
import com.airjnc.common.service.HashService;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
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
public class UserService {

  private final HashService hashService;

  private final UserModelMapper userModelMapper;

  private final UserRepository userRepository;

  private final UserCheckService userCheckService;

  private final RedisDao redisDao;

  private final CommonCheckService commonCheckService;

  public UserResp create(UserCreateReq userCreateReq) {
    userCheckService.emailShouldNotBeDuplicated(userCreateReq.getEmail());
    String hash = hashService.encrypt(userCreateReq.getPassword());
    UserEntity userEntity = userRepository.save(userCreateReq.toSaveDTO(hash));
    return userModelMapper.userEntityToUserResp(userEntity);
  }

  public void delete(Long currentUserId) {
    userRepository.delete(currentUserId);
  }

  public UserResp getUserById(Long userId) {
    UserEntity userEntity = userRepository.findById(userId);
    return userModelMapper.userEntityToUserResp(userEntity);
  }

  public UserResp getUserWithDeletedByEmail(String email) {
    UserEntity userEntity = userRepository.findWithDeletedByEmail(email);
    return userModelMapper.userEntityToUserResp(userEntity);
  }

  public UserInquiryEmailResp inquiryEmail(UserInquiryEmailReq userInquiryEmailReq) {
    UserEntity userEntity = userRepository.findWithDeletedByNameAndBirthDate(
        userInquiryEmailReq.getName(),
        userInquiryEmailReq.getBirthDate()
    );
    return userModelMapper.userEntityToUserInquiryEmailResp(userEntity);
  }

  public void resetPassword(UserResetPwdReq userResetPwdReq) {
    String code = redisDao.get(userResetPwdReq.getEmail());
    commonCheckService.shouldBeMatch(code, userResetPwdReq.getCode());
    redisDao.delete(userResetPwdReq.getEmail());
    String hash = hashService.encrypt(userResetPwdReq.getPassword());
    userRepository.updatePasswordByEmail(userResetPwdReq.getEmail(), hash);
  }

  public void restore(Long userId) {
    UserEntity userEntity = userRepository.findOnlyDeletedById(userId);
    userCheckService.shouldBeDeleted(userEntity);
    userRepository.restore(userId);
  }
}
