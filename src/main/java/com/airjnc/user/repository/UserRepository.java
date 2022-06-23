package com.airjnc.user.repository;

import com.airjnc.user.dto.UserDTO;
import com.airjnc.user.entity.UserEntity;

import java.util.Optional;


public interface UserRepository {

    public UserEntity findUserById(Long id);
    
}
