package com.airjnc.user.dao.impl;

import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.dao.mapper.UserMapper;
import com.airjnc.user.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MybatisUserRepository implements UserRepository {
    private final UserMapper userMapper;

    @Override
    public Optional<UserEntity> findById(Long id) {
        return this.userMapper.findById(id);
    }
}
