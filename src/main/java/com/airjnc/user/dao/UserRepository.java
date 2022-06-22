package com.airjnc.user.dao;

import com.airjnc.user.domain.UserEntity;

import java.util.Optional;

public interface UserRepository {
    Optional<UserEntity> findById(Long id);
}
