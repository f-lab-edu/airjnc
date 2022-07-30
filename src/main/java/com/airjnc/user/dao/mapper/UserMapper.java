package com.airjnc.user.dao.mapper;

import com.airjnc.user.dto.request.UserFindEmailDTO;
import com.airjnc.user.dto.UserSaveDTO;
import com.airjnc.user.dto.UserUpdatePwdByEmailDTO;
import com.airjnc.user.domain.UserEntity;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

  int remove(Long id);

  int save(UserSaveDTO userSaveDTO);

  Optional<String> getEmail(UserFindEmailDTO userFindEmailDTO);

  Optional<UserEntity> findById(Long id);

  Optional<UserEntity> findByEmail(String email);

  Optional<UserEntity> findByPhoneNumber(String phoneNumber);

  int updatePasswordByEmail(UserUpdatePwdByEmailDTO userUpdatePwdByEmailDTO);
}
