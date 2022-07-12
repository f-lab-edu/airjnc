package com.airjnc.user.service;

import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.util.validator.EmailDuplicateValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    /**
     * Spring에서 제공하는 Validator를 사용하여 비즈니스 로직 검증을 진행함
     * Why? 에러 메시지를 통합하기 위해 [MessageSource 사용]
     * ExceptionAdvice 에서 모두 Exception을 잡고 메시지를 전달해준다.
     */
    private final EmailDuplicateValidator emailDuplicateValidator;

    public UserDTO create(SignUpDTO signUpDTO) {
        // 이메일 중복 검사
        emailDuplicateValidator.validate(signUpDTO);
        // 비밀번호 "평문->해시" 로 해시화
        signUpDTO.changePasswordToHash();
        UserEntity userEntity = userRepository.save(signUpDTO);
        return modelMapper.map(userEntity, UserDTO.class);
    }

    public void remove(Long id) {
        userRepository.remove(id);
    }
}
