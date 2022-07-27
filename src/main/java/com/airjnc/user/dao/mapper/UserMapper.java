package com.airjnc.user.dao.mapper;

import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.CreateDTO;
import com.airjnc.user.dto.request.FindEmailDTO;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

  int remove(Long id);

  int save(CreateDTO createDTO);

  Optional<String> getEmail(FindEmailDTO findEmailDTO);

  Optional<UserEntity> findById(Long id);

  Optional<UserEntity> findByEmail(String email);
}
