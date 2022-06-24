package com.airjnc.user.service;

import com.airjnc.user.domain.User;
import com.airjnc.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public void join(User user) {
        userRepository.insertUser(user);
    }

    @Override
    public void checkDuplicateEmail(String email) {
    }
}
