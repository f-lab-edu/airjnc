package com.airjnc.user.service;

import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UserDto;
import com.airjnc.user.dto.request.AuthLogInReq;
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

  public UserResp logIn(AuthLogInReq authLogInReq) {
    UserEntity userEntity = userRepository.findByWhere(UserDto.builder().email(authLogInReq.getEmail()).build());
    userCheckService.passwordShouldBeMatch(authLogInReq.getPassword(), userEntity.getPassword());
    return userModelMapper.userEntityToUserResp(userEntity);
  }
}
