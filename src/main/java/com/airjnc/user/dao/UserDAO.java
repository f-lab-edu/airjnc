package com.airjnc.user.dao;

import com.airjnc.user.domain.User;

import java.util.Optional;


public interface UserDAO {

    public Optional<User> selectUserById(Long id);

    public Optional<User> selectUserByEmail(String email);

    public void insertUser(User userEntity);


}
