package com.airjnc.user.service;

import com.airjnc.common.error.exception.DuplicateException;
import com.airjnc.common.util.BCryptHashEncoder;
import com.airjnc.user.domain.User;
import com.airjnc.user.dto.request.LogInRequestDTO;
import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.dto.response.FindPwdResponseDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.exception.UserLoginNotMatchException;
import com.airjnc.user.mapper.UserMapper;
import com.airjnc.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public FindPwdResponseDTO findPasswordByEmail(String email) {
        Optional<User> user = userRepository.selectUserByEmail(email);
        return FindPwdResponseDTO.builder()
            .password(user.get().getPassword())
            .build();
    }

    @Override
    public UserDTO create(SignUpDTO signUpDTO) {
        checkDuplicateEmail(signUpDTO);
        User user = userMapper.signUpDTOtoUser(signUpDTO);
        user.encodePassword(signUpDTO.getPassword());
        User signupUser = userRepository.insertUser(user);
        UserDTO userDTO = userMapper.userToUserDTO(signupUser);
        return userDTO;
    }

    @Override
    public void logIn(LogInRequestDTO logInRequestDTO) {
        Optional<User> user = userRepository.selectUserByEmail(logInRequestDTO.getEmail());
        if (user.isPresent()) {
            if (BCryptHashEncoder.isMatch(logInRequestDTO.getPassword(), user.get().getPassword())) {
                return;
            }
        }
        throw new UserLoginNotMatchException();
    }


    private void checkDuplicateEmail(SignUpDTO signUpDTO) {
        Optional<User> user = userRepository.selectUserByEmail(signUpDTO.getEmail());
        if (user.isEmpty()) {
            return;
        }
        throw new DuplicateException("Email");
    }
}

    
