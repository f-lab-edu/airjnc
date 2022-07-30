package com.airjnc.user.dao.mapper;

import com.airjnc.user.dto.request.UserFindEmailDTO;
import com.airjnc.user.dto.UserSaveDTO;
import com.airjnc.user.dto.UserUpdatePwdByEmailDTO;
import com.airjnc.user.domain.UserEntity;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

  Optional<UserEntity> findByEmail(String email);

  Optional<UserEntity> findById(Long id);

  Optional<UserEntity> findByPhoneNumber(String phoneNumber);

  Optional<String> getEmail(UserFindEmailDTO userFindEmailDTO);

  int remove(Long id);

  int save(UserSaveDTO userSaveDTO);

  int updatePasswordByEmail(UserUpdatePwdByEmailDTO userUpdatePwdByEmailDTO);
}
