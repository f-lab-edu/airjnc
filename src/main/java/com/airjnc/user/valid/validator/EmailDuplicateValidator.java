package com.airjnc.user.valid.validator;

import com.airjnc.common.error.exception.DuplicateException;
import com.airjnc.user.domain.User;
import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmailDuplicateValidator {
    private final UserRepository userRepository;

    public boolean supports(Class<?> clazz) {
        return SignUpDTO.class.equals(clazz);
    }

    public void validate(Object target) {
        SignUpDTO signUpDTO = (SignUpDTO) target;
        Optional<User> user = userRepository.selectUserByEmail(signUpDTO.getEmail());
        if (user.isEmpty()) {
            return;
        }
        throw new DuplicateException("Email");
    }
}
