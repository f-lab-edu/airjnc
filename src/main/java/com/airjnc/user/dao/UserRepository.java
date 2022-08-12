package com.airjnc.user.dao;

import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UserDto;
import com.airjnc.user.dto.UserDto.UserStatus;
import com.airjnc.user.dto.UserSaveDto;

public interface UserRepository {

  void delete(Long id);

  UserEntity findById(Long userId, UserStatus status);

  UserEntity findByWhere(UserDto userDto);

  void restore(Long id);

  UserEntity save(UserSaveDto userSaveDTO);

  void updatePasswordByEmail(String email, String password);
}
