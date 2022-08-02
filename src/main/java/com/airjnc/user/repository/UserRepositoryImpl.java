package com.airjnc.user.repository;

import com.airjnc.user.dao.UserDAO;
import com.airjnc.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserDAO userDAO;

    @Override
    public Optional<User> selectUserById(Long id) {
        return userDAO.selectUserById(id);
    }

    @Override
    public Optional<User> selectUserByEmail(String email) {
        return userDAO.selectUserByEmail(email);
    }

    @Override
    public Optional<User> selectUserByNameAndPhoneNumber(String name, String phoneNumber) {
        return userDAO.selectUserByNameAndPhoneNumber(name, phoneNumber);
    }

    @Override
    public User insertUser(User user) {
        userDAO.insertUser(user);
        return user;
    }


}
