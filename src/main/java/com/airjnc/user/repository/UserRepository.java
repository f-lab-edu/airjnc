package com.airjnc.user.repository;

import com.airjnc.user.domain.User;


public interface UserRepository {

    public User selectUserById(Long id);

    public User selectUserByEmail(String email);

    public User insertUser(User user);
}