package com.airjnc.user.dao.mapper;

import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.CreateDTO;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

  Optional<UserEntity> findById(Long id);

  Optional<UserEntity> findByEmail(String email);

  int save(CreateDTO createDTO);

  int remove(Long id);
}
