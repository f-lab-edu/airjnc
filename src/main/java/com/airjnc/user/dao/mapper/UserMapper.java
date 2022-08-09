package com.airjnc.user.dao.mapper;

import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.CreateDTO;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

  Optional<UserEntity> findByEmail(String email);

  Optional<UserEntity> findById(Long id);

  int remove(Long id);

  int save(CreateDTO createDTO);
}
