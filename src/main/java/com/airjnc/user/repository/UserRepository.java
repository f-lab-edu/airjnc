package com.airjnc.user.repository;

import com.airjnc.user.domain.User;

import java.util.Optional;


public interface UserRepository {

    Optional<User> selectUserById(Long id);

    Optional<User> selectUserByEmail(String email);

    Optional<User> selectUserByNameAndPhoneNumber(String name, String phoneNumber);

    User insertUser(User user);
}