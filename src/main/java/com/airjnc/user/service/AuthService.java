package com.airjnc.user.service;

import com.airjnc.common.service.StateService;
import com.airjnc.common.util.enumerate.SessionKey;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UserWhereDto;
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

  private final StateService stateService;

  private final UserCheckService userCheckService;

  public UserResp logIn(AuthLogInReq authLogInReq) {
    UserWhereDto userWhereDto = UserWhereDto.builder().email(authLogInReq.getEmail()).build();
    UserEntity userEntity = userRepository.findByWhere(userWhereDto);
    userCheckService.passwordShouldBeMatch(authLogInReq.getPassword(), userEntity.getPassword());
    stateService.create(SessionKey.USER, userEntity.getId());
    return userModelMapper.userEntityToUserResp(userEntity);
  }
}
