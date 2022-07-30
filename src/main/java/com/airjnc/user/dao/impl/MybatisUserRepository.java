package com.airjnc.user.dao.impl;

import com.airjnc.common.exception.NotFoundException;
import com.airjnc.common.service.CommonInternalCheckService;
import com.airjnc.user.dto.request.UserFindEmailDTO;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.dto.UserSaveDTO;
import com.airjnc.user.dto.UserUpdatePwdByEmailDTO;
import com.airjnc.user.dao.mapper.UserMapper;
import com.airjnc.user.domain.UserEntity;
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
  public void remove(Long id) {
    int affect = userMapper.remove(id);
    commonInternalCheckService.shouldBeMatch(affect, 1);
  }

  @Override
  public UserEntity save(UserSaveDTO userSaveDTO) {
    int affect = userMapper.save(userSaveDTO);
    commonInternalCheckService.shouldBeMatch(affect, 1);
    return userModelMapper.saveDTOToUserEntity(userSaveDTO);
  }

  @Override
  public String getEmail(UserFindEmailDTO userFindEmailDTO) {
    return userMapper.getEmail(userFindEmailDTO).orElseThrow(NotFoundException::new);
  }

  @Override
  public UserEntity findById(Long id) {
    return userMapper.findById(id).orElseThrow(NotFoundException::new);
  }

  @Override
  public UserEntity findByEmail(String email) {
    return userMapper.findByEmail(email).orElseThrow(NotFoundException::new);
  }

  @Override
  public UserEntity findByPhoneNumber(String phoneNumber) {
    return userMapper.findByPhoneNumber(phoneNumber).orElseThrow(NotFoundException::new);
  }

  @Override
  public void updatePasswordByEmail(UserUpdatePwdByEmailDTO userUpdatePwdByEmailDTO) {
    int affect = userMapper.updatePasswordByEmail(userUpdatePwdByEmailDTO);
    commonInternalCheckService.shouldBeMatch(affect, 1);
  }
}
