package com.airjnc.user.service;

import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.CreateDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.util.UserModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserModelMapper userModelMapper;

  private final UserRepository userRepository;

  private final UserCheckService userCheckService;

  public UserDTO create(CreateDTO createDTO) {
    userCheckService.emailShouldNotBeDuplicated(createDTO.getEmail());
    createDTO.changePasswordToHash();
    UserEntity userEntity = userRepository.save(createDTO);
    return userModelMapper.userEntityToUserDTO(userEntity);
  }

  public void remove(Long id) {
    userRepository.remove(id);
  }
}
