package com.airjnc.user.service;

import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.dto.response.UserDTO;

public interface UserService {
    
    public UserDTO findPasswordByEmail(String email);

    public UserDTO create(SignUpDTO signUpDTO);

    public void checkDuplicateEmail(String email);
}
