package com.airjnc.user.service;

import com.airjnc.common.util.validator.ValidatorTemplate;
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
    /**
     * Validator를 컨트롤러 계층이 아닌 서비스 계층에서 사용할 경우, BindingResult를 직접 생성해주어야함
     * Validaotr를 사용할 때마다 BindingResult를 생성 로직 작성을 피하기 위해 VliadatorTemplate으로 묶음
     */
    private final ValidatorTemplate validatorTemplate;

    public UserDTO create(SignUpDTO signUpDTO) {
        // 이메일 중복 검사
        this.validatorTemplate.validate(emailDuplicateValidator, signUpDTO);
        // 비밀번호 "평문->해시" 로 해시화
        signUpDTO.changePasswordToHash();
        UserEntity user = this.userRepository.save(signUpDTO);
        return this.modelMapper.map(user, UserDTO.class);
    }
}
