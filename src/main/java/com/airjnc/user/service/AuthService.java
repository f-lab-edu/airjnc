package com.airjnc.user.service;

import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.PasswordMatchDTO;
import com.airjnc.user.dto.request.LogInDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.util.UserModelMapper;
import com.airjnc.user.util.validator.PasswordMatchValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;

  private final PasswordMatchValidator passwordMatchValidator;

  private final UserModelMapper userModelMapper;

  public UserDTO logIn(LogInDTO logInDTO) {
    UserEntity userEntity = userRepository.findByEmail(logInDTO.getEmail());
    PasswordMatchDTO passwordMatchDTO = PasswordMatchDTO.builder()
        .password(logInDTO.getPassword())
        .hash(userEntity.getPassword())
        .build();
    passwordMatchValidator.validate(passwordMatchDTO);
    return userModelMapper.userEntityToUserDTO(userEntity);
  }
}
