package com.airjnc.user.dao;

import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UserSaveDto;
import com.airjnc.user.dto.UserUpdateDto;
import java.time.LocalDate;

public interface UserRepository {

  void delete(Long id);

  UserEntity findByEmail(String email);

  UserEntity findById(Long id);

  UserEntity findByPhoneNumber(String phoneNumber);

  UserEntity findOnlyDeletedById(Long id);

  UserEntity findWithDeletedByEmail(String email);

  UserEntity findWithDeletedByNameAndBirthDate(String name, LocalDate birthDate);

  void restore(Long id);

  UserEntity save(UserSaveDto userSaveDTO);

  void updatePasswordByEmail(String email, String password);

  void updateUserById(UserUpdateDto userUpdateDto);
}
