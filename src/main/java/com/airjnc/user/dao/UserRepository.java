package com.airjnc.user.dao;

import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UserWhereDto;
import com.airjnc.user.dto.UserWhereDto.UserStatus;

public interface UserRepository {

  UserEntity create(UserEntity userEntity);

  boolean exists(UserWhereDto userWhereDto);

  UserEntity findById(Long userId, UserStatus status);

  UserEntity findByWhere(UserWhereDto userWhereDto);

  UserEntity save(UserEntity userEntity);
}
