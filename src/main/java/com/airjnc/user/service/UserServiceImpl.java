package com.airjnc.user.service;

import com.airjnc.user.domain.User;
import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.mapper.UserMapper;
import com.airjnc.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    UserMapper userMapper;
    
    @Override
    public UserDTO findPasswordByEmail(String email){
        User user = userRepository.selectUserByEmail(email);
        return UserDTO.builder()
            .password(user.getPassword())
            .build();
    }

    @Override
    public UserDTO create(SignUpDTO signUpDTO) {
        User user = userMapper.signUpDTOtoUser(signUpDTO);
        User signupUser = userRepository.insertUser(user);
        UserDTO userDTO = userMapper.userToUserDTO(signupUser);
        
        return userDTO;
    }

    @Override
    public void checkDuplicateEmail(String email) {
        
    }
}
