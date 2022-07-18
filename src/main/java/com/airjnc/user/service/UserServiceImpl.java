package com.airjnc.user.service;

import com.airjnc.common.util.BCryptHashEncoder;
import com.airjnc.user.domain.User;
import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.dto.response.FindPwdResponseDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.mapper.UserMapper;
import com.airjnc.user.repository.UserRepository;
import com.airjnc.user.valid.validator.EmailDuplicateValidator;
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
    private final EmailDuplicateValidator emailDuplicateValidator;

    @Override
    public FindPwdResponseDTO findPasswordByEmail(String email) {
        Optional<User> user = userRepository.selectUserByEmail(email);
        return FindPwdResponseDTO.builder()
            .password(user.get().getPassword())
            .build();
    }

    @Override
    public UserDTO create(SignUpDTO signUpDTO) {
        emailDuplicateValidator.validate(signUpDTO);
        User user = userMapper.signUpDTOtoUser(signUpDTO);

        user.encodePassword(signUpDTO.getPassword());
        User signupUser = userRepository.insertUser(user);
        UserDTO userDTO = userMapper.userToUserDTO(signupUser);
        return userDTO;
    }

    @Override
    public void checkDuplicateEmail(String email) {
    }


    private String encodePassword(String password) {
        return BCryptHashEncoder.encode(password);
    }

}
