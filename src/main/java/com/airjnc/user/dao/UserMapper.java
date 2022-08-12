package com.airjnc.user.dao;

import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UserDto;
import com.airjnc.user.dto.UserDto.UserStatus;
import com.airjnc.user.dto.UserSaveDto;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

  int delete(Long id);

  Optional<UserEntity> findById(@Param("id") Long userId, @Param("status") UserStatus status);

  Optional<UserEntity> findByWhere(UserDto userDto);

  int create(UserSaveDto userSaveDTO);

  int save(UserEntity userEntity);
}
