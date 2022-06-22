package com.airjnc.user.dao.mapper;

import com.airjnc.user.domain.UserEntity;

import java.util.Optional;

public interface UserMapper {
    Optional<UserEntity> findById(Long id);
}
