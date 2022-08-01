package com.airjnc.user.service;

import com.airjnc.common.auth.dto.AuthInfoDTO;
import com.airjnc.user.dto.request.LogInRequestDTO;
import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.dto.response.FindPwdResponseDTO;
import com.airjnc.user.dto.response.UserDTO;

public interface UserService {

    public FindPwdResponseDTO findPasswordByEmail(String email);

    public UserDTO create(SignUpDTO signUpDTO);

    public AuthInfoDTO logIn(LogInRequestDTO logInRequestDTO);
}
