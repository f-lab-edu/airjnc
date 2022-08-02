package com.airjnc.user.dao;

import com.airjnc.user.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;


public interface UserDAO {

    public Optional<User> selectUserById(Long id);

    public Optional<User> selectUserByEmail(String email);

    public Optional<User> selectUserByNameAndPhoneNumber(@Param("name") String name, @Param("phoneNumber") String phoneNumber);

    public void insertUser(User userEntity);


}
