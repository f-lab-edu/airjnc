package com.airjnc.user.service;

import com.airjnc.common.auth.dto.AuthInfoDTO;
import com.airjnc.user.dto.request.FindEmailRequestDTO;
import com.airjnc.user.dto.request.LogInRequestDTO;
import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.dto.response.FindEmailResponseDTO;
import com.airjnc.user.dto.response.FindPwdResponseDTO;
import com.airjnc.user.dto.response.UserDTO;

public interface UserService {

    FindEmailResponseDTO findEmailByNameAndPhoneNumber(FindEmailRequestDTO findEmailRequestDTO);

    FindPwdResponseDTO findPasswordByEmail(String email);

    UserDTO create(SignUpDTO signUpDTO);

    AuthInfoDTO logIn(LogInRequestDTO logInRequestDTO);
}
