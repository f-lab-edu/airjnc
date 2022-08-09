package com.airjnc.user.dao;

import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UserSaveDto;
import java.time.LocalDate;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

  int delete(Long id);

  Optional<UserEntity> findByEmail(String email);

  Optional<UserEntity> findById(Long id);

  Optional<UserEntity> findByPhoneNumber(String phoneNumber);

  Optional<UserEntity> findOnlyDeletedById(Long id);

  Optional<UserEntity> findWithDeletedByEmail(String email);

  Optional<UserEntity> findWithDeletedByNameAndBirthDate(
      @Param("name") String name,
      @Param("birthDate") LocalDate birthDate
  );

  int restore(Long id);

  int save(UserSaveDto userSaveDTO);

  int updatePasswordByEmail(@Param("email") String email, @Param("password") String password);
}
