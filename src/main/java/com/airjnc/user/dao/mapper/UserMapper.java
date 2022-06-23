package com.airjnc.user.dao.mapper;

import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.SignUpDTO;

import java.util.Optional;

public interface UserMapper {
    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByEmail(String email);

    int save(SignUpDTO signUpDTO);
}
