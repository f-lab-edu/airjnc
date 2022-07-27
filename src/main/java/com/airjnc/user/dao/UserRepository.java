package com.airjnc.user.dao;

import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.CreateDTO;
import com.airjnc.user.dto.request.FindEmailDTO;

public interface UserRepository {

  void remove(Long id);

  UserEntity save(CreateDTO createDTO);

  String getEmail(FindEmailDTO findEmailDTO);

  UserEntity findByEmail(String email);

  UserEntity findById(Long id);
}
