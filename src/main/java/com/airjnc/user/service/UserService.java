package com.airjnc.user.service;

import com.airjnc.user.domain.User;

public interface UserService {

    public void join(User user);

    public void checkDuplicateEmail(String email);
}
