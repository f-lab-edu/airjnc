package com.airjnc.user.service;

import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.UserCreateReq;
import com.airjnc.user.dto.response.UserResp;
import com.airjnc.user.util.UserModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserModelMapper userModelMapper;

  private final UserRepository userRepository;

  private final UserCheckService userCheckService;

  public UserResp create(UserCreateReq userCreateReq) {
    userCheckService.emailShouldNotBeDuplicated(userCreateReq.getEmail());
    UserEntity userEntity = userRepository.save(userCreateReq.toSaveDTO());
    return userModelMapper.userEntityToUserDTO(userEntity);
  }

  public void remove(Long id) {
    userRepository.remove(id);
  }
}
