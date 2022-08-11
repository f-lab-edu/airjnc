package com.airjnc.user.dao.impl;

import com.airjnc.common.exception.NotFoundException;
import com.airjnc.common.service.CommonCheckService;
import com.airjnc.user.dao.UserMapper;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UserSaveDto;
import com.airjnc.user.dto.UserUpdateDto;
import com.airjnc.user.util.UserModelMapper;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisUserRepository implements UserRepository {

  private final UserMapper userMapper;

  private final CommonCheckService commonCheckService;

  private final UserModelMapper userModelMapper;

  public void delete(Long id) {
    int affect = userMapper.delete(id);
    commonCheckService.shouldBeMatch(affect, 1);
  }

  @Override
  public UserEntity findByEmail(String email) {
    return userMapper.findByEmail(email).orElseThrow(NotFoundException::new);
  }

  @Override
  public UserEntity findById(Long id) {
    return userMapper.findById(id).orElseThrow(NotFoundException::new);
  }

  @Override
  public UserEntity findByPhoneNumber(String phoneNumber) {
    return userMapper.findByPhoneNumber(phoneNumber).orElseThrow(NotFoundException::new);
  }

  @Override
  public UserEntity findOnlyDeletedById(Long id) {
    return userMapper.findOnlyDeletedById(id).orElseThrow(NotFoundException::new);
  }

  @Override
  public UserEntity findWithDeletedByEmail(String email) {
    return userMapper.findWithDeletedByEmail(email).orElseThrow(NotFoundException::new);
  }

  @Override
  public UserEntity findWithDeletedByNameAndBirthDate(String name, LocalDate birthDate) {
    return userMapper.findWithDeletedByNameAndBirthDate(name, birthDate).orElseThrow(NotFoundException::new);
  }

  @Override
  public void restore(Long id) {
    int affect = userMapper.restore(id);
    commonCheckService.shouldBeMatch(affect, 1);
  }

  @Override
  public UserEntity save(UserSaveDto userSaveDTO) {
    int affect = userMapper.save(userSaveDTO);
    commonCheckService.shouldBeMatch(affect, 1);
    return userModelMapper.userSaveDtoToUserEntity(userSaveDTO);
  }

  @Override
  public void updatePasswordByEmail(String email, String password) {
    int affect = userMapper.updatePasswordByEmail(email, password);
    commonCheckService.shouldBeMatch(affect, 1);
  }

  @Override
  public void updateUserById(UserUpdateDto userUpdateDto) {
    int affect = userMapper.updateUserById(userUpdateDto);
    commonCheckService.shouldBeMatch(affect, 1);
  }
}
