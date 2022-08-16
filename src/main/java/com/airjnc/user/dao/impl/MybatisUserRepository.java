package com.airjnc.user.dao.impl;

import com.airjnc.common.exception.NotFoundException;
import com.airjnc.common.service.CommonCheckService;
import com.airjnc.user.dao.UserMapper;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UserWhereDto;
import com.airjnc.user.dto.UserWhereDto.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisUserRepository implements UserRepository {

  private final UserMapper userMapper;

  private final CommonCheckService commonCheckService;

  @Override
  public UserEntity create(UserEntity userEntity) {
    int affect = userMapper.create(userEntity);
    commonCheckService.shouldBeMatch(affect, 1);
    return userEntity;
  }

  @Override
  public boolean exists(UserWhereDto userWhereDto) {
    return userMapper.exists(userWhereDto);
  }

  @Override
  public UserEntity findById(Long userId, UserStatus status) {
    return userMapper.findById(userId, status).orElseThrow(NotFoundException::new);
  }

  @Override
  public UserEntity findByWhere(UserWhereDto userWhereDto) {
    return userMapper.findByWhere(userWhereDto).orElseThrow(NotFoundException::new);
  }

  @Override
  public UserEntity save(UserEntity userEntity) {
    int affect = userMapper.save(userEntity);
    commonCheckService.shouldBeMatch(affect, 1);
    return userEntity;
  }
}
