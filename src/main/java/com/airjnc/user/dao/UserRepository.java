package com.airjnc.user.dao;

import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.CreateDTO;

public interface UserRepository {
    UserEntity findById(Long id);

    UserEntity findByEmail(String email);

    UserEntity save(CreateDTO createDTO);

    void remove(Long id);
}
