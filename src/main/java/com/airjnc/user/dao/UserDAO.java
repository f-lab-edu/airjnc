package com.airjnc.user.dao;

import com.airjnc.user.dto.UserDTO;
import com.airjnc.user.entity.UserEntity;
import org.springframework.context.annotation.Bean;

import java.util.Optional;


public interface UserDAO {
    
    public UserEntity findUserById(Long id);
}
