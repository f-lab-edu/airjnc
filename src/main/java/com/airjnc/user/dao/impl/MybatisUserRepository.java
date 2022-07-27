package com.airjnc.user.dao.impl;

import com.airjnc.common.exception.NotFoundException;
import com.airjnc.common.service.CommonInternalCheckService;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.dao.mapper.UserMapper;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.CreateDTO;
import com.airjnc.user.dto.request.FindEmailDTO;
import com.airjnc.user.util.UserModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisUserRepository implements UserRepository {

  private final UserMapper userMapper;

  private final CommonInternalCheckService commonInternalCheckService;

  private final UserModelMapper userModelMapper;

  @Override
  public UserEntity findById(Long id) {
    return userMapper.findById(id).orElseThrow(NotFoundException::new);
  }

  @Override
  public UserEntity findByEmail(String email) {
    return userMapper.findByEmail(email).orElseThrow(NotFoundException::new);
  }

  @Override
  public String getEmail(FindEmailDTO findEmailDTO) {
    return userMapper.getEmail(findEmailDTO);
  }

  @Override
  public UserEntity save(CreateDTO createDTO) {
    int affect = userMapper.save(createDTO);
    commonInternalCheckService.shouldBeMatch(affect, 1);
    return userModelMapper.createDTOToUserEntity(createDTO);
  }

  @Override
  public void remove(Long id) {
    int affect = userMapper.remove(id);
    commonInternalCheckService.shouldBeMatch(affect, 1);
  }
}
