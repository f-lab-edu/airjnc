package com.airjnc.user.service;

import com.airjnc.common.dao.RedisDao;
import com.airjnc.common.properties.SessionTtlProperties;
import com.airjnc.common.service.CommonUtilService;
import com.airjnc.common.service.HashService;
import com.airjnc.mail.dto.SendUsingTemplateDto;
import com.airjnc.mail.service.MailService;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.UserCreateReq;
import com.airjnc.user.dto.request.UserInquiryEmailReq;
import com.airjnc.user.dto.request.UserInquiryPasswordViaEmailReq;
import com.airjnc.user.dto.request.UserResetPwdReq;
import com.airjnc.user.dto.request.inquiryPasswordViaPhoneReq;
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

  private final MailService mailService;

  private final CommonUtilService commonUtilService;

  private final RedisDao redisDao;

  private final SessionTtlProperties sessionTtlProperties;

  public UserResp create(UserCreateReq userCreateReq) {
    userCheckService.emailShouldNotBeDuplicated(userCreateReq.getEmail());
    String hash = hashService.encrypt(userCreateReq.getPassword());
    UserEntity userEntity = userRepository.save(userCreateReq.toSaveDTO(hash));
    return userModelMapper.userEntityToUserResp(userEntity);
  }

  public UserInquiryEmailResp inquiryEmail(UserInquiryEmailReq userInquiryEmailReq) {
    UserEntity userEntity = userRepository.findWithDeletedByNameAndBirthDate(
        userInquiryEmailReq.getName(),
        userInquiryEmailReq.getBirthDate()
    );
    return userModelMapper.userEntityToUserInquiryEmailResp(userEntity);
  }

  public void delete(Long currentUserId) {
    userRepository.delete(currentUserId);
  }

  private String generateAndRestoreCode(String email) {
    String code = commonUtilService.generateCode();
    redisDao.store(code, email, sessionTtlProperties.getResetPasswordCode());
    return code;
  }

  public void inquiryPasswordViaEmail(UserInquiryPasswordViaEmailReq userInquiryPasswordViaEmailReq) {
    UserEntity user = userRepository.findByEmail(userInquiryPasswordViaEmailReq.getEmail());
    String code = generateAndRestoreCode(user.getEmail());
    mailService.send(
        userInquiryPasswordViaEmailReq.getEmail(),
        SendUsingTemplateDto.builder()
            .name(user.getName())
            .code(code)
            .build()
    );
  }

  public void inquiryPasswordViaPhone(inquiryPasswordViaPhoneReq inquiryPasswordViaPhoneReq) {
    UserEntity user = userRepository.findByPhoneNumber(inquiryPasswordViaPhoneReq.getPhone());
    String code = generateAndRestoreCode(user.getEmail());
    // TODO: send sms
  }

  public void resetPassword(UserResetPwdReq userResetPwdReq) {
    String email = redisDao.get(userResetPwdReq.getCode());
    redisDao.delete(userResetPwdReq.getCode());
    String hash = hashService.encrypt(userResetPwdReq.getPassword());
    userRepository.updatePasswordByEmail(email, hash);
  }

  public void restore(Long userId) {
    UserEntity user = userRepository.findOnlyDeletedById(userId);
    userCheckService.shouldBeDeleted(user);
    userRepository.restore(userId);
  }
}
