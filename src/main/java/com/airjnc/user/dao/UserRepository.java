package com.airjnc.user.dao;

import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.CreateDTO;
import com.airjnc.user.dto.request.FindEmailDTO;

public interface UserRepository {

  UserEntity findById(Long id);

  UserEntity findByEmail(String email);

  String getEmail(FindEmailDTO findEmailDTO);

  UserEntity save(CreateDTO createDTO);


  void remove(Long id);
}
