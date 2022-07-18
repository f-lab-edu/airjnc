package com.airjnc.user.service;

import com.airjnc.common.error.exception.DuplicateException;
import com.airjnc.user.domain.User;
import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.dto.response.FindPwdResponseDTO;
import com.airjnc.user.dto.response.UserDTO;
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

    private void checkDuplicateEmail(SignUpDTO signUpDTO) {
        Optional<User> user = userRepository.selectUserByEmail(signUpDTO.getEmail());
        if (user.isEmpty()) {
            return;
        }
        throw new DuplicateException("Email");
    }
}

    
