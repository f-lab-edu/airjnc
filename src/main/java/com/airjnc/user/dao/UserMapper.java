package com.airjnc.user.dao;

import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UserSaveDto;
import com.airjnc.user.dto.response.UserInquiryEmailResp;
import java.time.LocalDate;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

  Optional<UserEntity> findByEmail(String email);

  Optional<UserEntity> findById(Long id);

  Optional<UserEntity> findByPhoneNumber(String phoneNumber);

  Optional<UserInquiryEmailResp> findEmailByNameAndBirthDate(
      @Param("name") String name,
      @Param("birthDate") LocalDate birthDate
  );

  int delete(Long id);

  int save(UserSaveDto userSaveDTO);

  int updatePasswordByEmail(@Param("email") String email, @Param("password") String password);
}