package com.airjnc.user.dao.impl;

import com.airjnc.common.util.validator.CommonValidator;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.dao.mapper.UserMapper;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.SignUpDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MybatisUserRepository implements UserRepository {
    private final UserMapper userMapper;
    private final ModelMapper modelMapper;
    private final CommonValidator commonValidator;


    @Override

    public Optional<UserEntity> findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    @Override
    public UserEntity save(SignUpDTO signUpDTO) {
        int affect = userMapper.save(signUpDTO);
        commonValidator.validateEqual(affect, 1);
        return modelMapper.map(signUpDTO, UserEntity.class);
    }

    @Override
    public void remove(Long id) {
        int affect = userMapper.remove(id);
        commonValidator.validateEqual(affect, 1);
    }
}
