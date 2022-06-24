package com.airjnc.user.repository;

import com.airjnc.user.dao.UserDAO;
import com.airjnc.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {


    @Autowired
    private UserDAO userDAO;

    @Override
    public User selectUserById(Long id) {
        return userDAO.selectUserById(id);
    }

    @Override
    public User selectUserByEmail(String email) {
        return userDAO.selectUserByEmail(email);
    }

    @Override
    public void insertUser(User user) {
        userDAO.insertUser(user);
    }


}
