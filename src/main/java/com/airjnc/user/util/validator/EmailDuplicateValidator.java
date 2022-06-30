package com.airjnc.user.util.validator;

import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.exception.DuplicatedEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmailDuplicateValidator {
    private final UserRepository userRepository;

    public void validate(Object target) {
        SignUpDTO signUpDTO = (SignUpDTO) target;
        Optional<UserEntity> user = userRepository.findByEmail(signUpDTO.getEmail());
        if (user.isEmpty()) {
            return;
        }
        throw new DuplicatedEmailException();
    }
}
