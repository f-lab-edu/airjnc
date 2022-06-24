package com.airjnc.user.dao;

import com.airjnc.user.domain.User;


public interface UserDAO {

    public User selectUserById(Long id);

    public User selectUserByEmail(String email);

    public void insertUser(User userEntity);
    

}
