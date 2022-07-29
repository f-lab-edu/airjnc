package com.airjnc.user.service;

import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.LogInDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.util.UserModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;

  private final UserModelMapper userModelMapper;

  private final UserCheckService userCheckService;

  public UserDTO logIn(LogInDTO logInDTO) {
    UserEntity userEntity = userRepository.findByEmail(logInDTO.getEmail());
    userCheckService.passwordShouldBeMatch(logInDTO.getPassword(), userEntity.getPassword());
    return userModelMapper.userEntityToUserDTO(userEntity);
  }
}
