package com.airjnc.user.dao;

import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UserSaveDto;
import com.airjnc.user.dto.response.UserInquiryEmailResp;
import java.time.LocalDate;

public interface UserRepository {

  void delete(Long id);

  UserEntity findByEmail(String email);

  UserEntity findById(Long id);

  UserEntity findByPhoneNumber(String phoneNumber);

  UserInquiryEmailResp findEmailByNameAndBirthDate(String name, LocalDate birthDate);

  UserEntity findOnlyDeletedById(Long id);

  UserEntity findWithDeletedByEmail(String email);

  void restore(Long id);

  UserEntity save(UserSaveDto userSaveDTO);

  void updatePasswordByEmail(String email, String password);
}
