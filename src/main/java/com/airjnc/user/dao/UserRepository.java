package com.airjnc.user.dao;

import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UserSaveDto;

public interface UserRepository {

  UserEntity findByEmail(String email);

  UserEntity findById(Long id);

  void remove(Long id);

  UserEntity save(UserSaveDto userSaveDto);
}
