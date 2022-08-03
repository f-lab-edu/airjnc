package com.airjnc.user.dao;

import com.airjnc.user.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;


public interface UserDAO {

    Optional<User> selectUserById(Long id);

    Optional<User> selectUserByEmail(String email);

    Optional<User> selectUserByNameAndPhoneNumber(@Param("name") String name, @Param("phoneNumber") String phoneNumber);

    void insertUser(User userEntity);


}
