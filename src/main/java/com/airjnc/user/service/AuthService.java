package com.airjnc.user.service;

import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.UserLogInReq;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.util.UserModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;

  private final UserModelMapper userModelMapper;

  private final UserCheckService userCheckService;

  public UserResp logIn(UserLogInReq userLogInReq) {
    UserEntity userEntity = userRepository.findByEmail(userLogInReq.getEmail());
    userCheckService.passwordShouldBeMatch(userLogInReq.getPassword(), userEntity.getPassword());
    return userModelMapper.userEntityToUserDTO(userEntity);
  }
}
