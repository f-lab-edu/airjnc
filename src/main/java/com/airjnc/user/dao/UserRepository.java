package com.airjnc.user.dao;

import com.airjnc.user.dto.request.UserFindEmailDTO;
import com.airjnc.user.dto.UserSaveDTO;
import com.airjnc.user.dto.UserUpdatePwdByEmailDTO;
import com.airjnc.user.domain.UserEntity;

public interface UserRepository {

  void remove(Long id);

  UserEntity save(UserSaveDTO userSaveDTO);

  String getEmail(UserFindEmailDTO userFindEmailDTO);

  UserEntity findById(Long id);

  UserEntity findByEmail(String email);

  UserEntity findByPhoneNumber(String phoneNumber);

  void updatePasswordByEmail(UserUpdatePwdByEmailDTO userUpdatePwdByEmailDTO);
}
