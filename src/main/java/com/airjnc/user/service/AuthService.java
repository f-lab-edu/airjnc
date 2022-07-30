package com.airjnc.user.service;

import com.airjnc.user.dto.request.AuthLogInDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.util.UserModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;

  private final UserModelMapper userModelMapper;

  private final UserCheckService userCheckService;

  public UserDTO logIn(AuthLogInDTO authLogInDTO) {
    UserEntity userEntity = userRepository.findByEmail(authLogInDTO.getEmail());
    userCheckService.passwordShouldBeMatch(authLogInDTO.getPassword(), userEntity.getPassword());
    return userModelMapper.userEntityToUserDTO(userEntity);
  }
}
