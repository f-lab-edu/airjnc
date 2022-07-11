package com.airjnc.user.util.validator;

import com.airjnc.common.exception.NotFoundException;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.dto.request.CreateDTO;
import com.airjnc.user.exception.DuplicatedEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailDuplicateValidator {
    private final UserRepository userRepository;

    public void validate(Object target) {
        CreateDTO createDTO = (CreateDTO) target;
        try {
            userRepository.findByEmail(createDTO.getEmail());
        } catch (NotFoundException e) {
            return;
        }
        throw new DuplicatedEmailException();
    }
}
