package com.airjnc.user.repository;

import com.airjnc.user.dao.UserDAO;
import com.airjnc.user.dto.UserDTO;
import com.airjnc.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository{

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserEntity findUserById(Long id) {
        return userDAO.findUserById(id);
    }
}
