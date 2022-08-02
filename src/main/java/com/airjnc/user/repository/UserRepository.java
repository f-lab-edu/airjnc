package com.airjnc.user.repository;

import com.airjnc.user.domain.User;

import java.util.Optional;


public interface UserRepository {

    public Optional<User> selectUserById(Long id);

    public Optional<User> selectUserByEmail(String email);
    
    public Optional<User> selectUserByNameAndPhoneNumber(String name, String phoneNumber);

    public User insertUser(User user);
}