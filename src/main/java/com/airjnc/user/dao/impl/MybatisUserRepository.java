package com.airjnc.user.dao.impl;

import com.airjnc.common.exception.NotFoundException;
import com.airjnc.common.util.ModelMapper;
import com.airjnc.common.util.validator.CommonValidator;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.dao.mapper.UserMapper;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.CreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisUserRepository implements UserRepository {
    private final UserMapper userMapper;
    private final CommonValidator commonValidator;
    private final ModelMapper modelMapper;

    @Override
    public UserEntity findById(Long id) {
        return userMapper.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userMapper.findByEmail(email).orElseThrow(NotFoundException::new);
    }

    @Override
    public UserEntity save(CreateDTO createDTO) {
        int affect = userMapper.save(createDTO);
        commonValidator.validateEqual(affect, 1);
        return modelMapper.createDTOToUserEntity(createDTO);
    }

    @Override
    public void remove(Long id) {
        int affect = userMapper.remove(id);
        commonValidator.validateEqual(affect, 1);
    }
}
