package com.airjnc.user.util.validator;

import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.util.exception.DuplicatedEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmailDuplicateValidator implements Validator {
    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return SignUpDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpDTO signUpDTO = (SignUpDTO) target;
        Optional<UserEntity> user = this.userRepository.findByEmail(signUpDTO.getEmail());
        if (user.isEmpty()) {
            return;
        }
        errors.rejectValue("email", "duplicated");
        throw new DuplicatedEmailException(errors);

    }
}
