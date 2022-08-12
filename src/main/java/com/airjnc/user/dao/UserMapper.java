package com.airjnc.user.dao;

import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UserWhereDto;
import com.airjnc.user.dto.UserWhereDto.UserStatus;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

  int create(UserEntity userEntity);

  boolean exists(UserWhereDto userWhereDto);

  Optional<UserEntity> findById(@Param("id") Long userId, @Param("status") UserStatus status);

  Optional<UserEntity> findByWhere(UserWhereDto userWhereDto);

  int save(UserEntity userEntity);
}
