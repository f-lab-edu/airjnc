package com.airjnc.user.service;

import com.airjnc.common.util.ModelMapper;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.CreateDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.util.validator.EmailDuplicateValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final EmailDuplicateValidator emailDuplicateValidator;

    public UserDTO create(CreateDTO createDTO) {
        emailDuplicateValidator.validate(createDTO);
        createDTO.changePasswordToHash();
        UserEntity userEntity = userRepository.save(createDTO);
        return modelMapper.userEntityToUserDTO(userEntity);
    }

    public void remove(Long id) {
        userRepository.remove(id);
    }
}
