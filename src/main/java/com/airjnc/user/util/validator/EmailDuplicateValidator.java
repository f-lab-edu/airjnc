package com.airjnc.user.util.validator;

import com.airjnc.common.exception.NotFoundException;
import com.airjnc.common.util.constant.ErrorCode;
import com.airjnc.common.util.validator.DefaultValidator;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.dto.request.CreateDTO;
import com.airjnc.user.exception.DuplicatedEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@RequiredArgsConstructor
public class EmailDuplicateValidator implements DefaultValidator {

  private final UserRepository userRepository;


  @Override
  public boolean supports(Class<?> clazz) {
    return CreateDTO.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    CreateDTO createDTO = (CreateDTO) target;
    try {
      userRepository.findByEmail(createDTO.getEmail());
    } catch (NotFoundException e) {
      return;
    }
        /*
        1. Duplicated.CreateDTO.email
        2. Duplicated.email
        3. Duplicated
         */
    errors.rejectValue("email", ErrorCode.DUPLICATED.name());
    throw new DuplicatedEmailException(errors);
  }
}
