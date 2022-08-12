package com.airjnc.user.dao;

import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UserDto;
import com.airjnc.user.dto.UserDto.UserStatus;
import com.airjnc.user.dto.UserSaveDto;

public interface UserRepository {

  void delete(Long id);

  UserEntity findById(Long userId, UserStatus status);

  UserEntity findByWhere(UserDto userDto);

  UserEntity create(UserSaveDto userSaveDTO);

  UserEntity save(UserEntity userEntity);
}
