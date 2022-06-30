package com.airjnc.user.dao;

import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.SignUpDTO;

import java.util.Optional;

public interface UserRepository {
    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByEmail(String email);

    UserEntity save(SignUpDTO signUpDTO);

    void remove(Long id);
}
