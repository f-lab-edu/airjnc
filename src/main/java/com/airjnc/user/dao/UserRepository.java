package com.airjnc.user.dao;

import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UserSaveDto;
import java.time.LocalDate;

public interface UserRepository {

  UserEntity findByEmail(String email);

  UserEntity findById(Long id);

  UserEntity findByPhoneNumber(String phoneNumber);

  UserEntity findWithDeletedByNameAndBirthDate(String name, LocalDate birthDate);

  void delete(Long id);

  UserEntity save(UserSaveDto userSaveDTO);

  void updatePasswordByEmail(String email, String password);
}
