package com.airjnc.user.dao;

import com.airjnc.user.dto.request.UserFindEmailDTO;
import com.airjnc.user.dto.UserSaveDTO;
import com.airjnc.user.dto.UserUpdatePwdByEmailDTO;
import com.airjnc.user.domain.UserEntity;

public interface UserRepository {

  UserEntity findByEmail(String email);

  UserEntity findById(Long id);

  UserEntity findByPhoneNumber(String phoneNumber);

  String getEmail(UserFindEmailDTO userFindEmailDTO);

  void remove(Long id);

  UserEntity save(UserSaveDTO userSaveDTO);

  void updatePasswordByEmail(UserUpdatePwdByEmailDTO userUpdatePwdByEmailDTO);
}
