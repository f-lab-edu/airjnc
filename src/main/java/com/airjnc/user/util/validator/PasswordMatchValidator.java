package com.airjnc.user.util.validator;

import com.airjnc.common.util.BCryptHashEncrypter;
import com.airjnc.common.util.constant.ErrorCode;
import com.airjnc.common.util.validator.DefaultValidator;
import com.airjnc.user.dto.PasswordMatchDTO;
import com.airjnc.user.exception.PasswordIsNotMatchException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class PasswordMatchValidator implements DefaultValidator {

  @Override
  public boolean supports(Class<?> clazz) {
    return PasswordMatchDTO.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    PasswordMatchDTO dto = (PasswordMatchDTO) target;
    boolean isMatch = BCryptHashEncrypter.isMatch(dto.getPassword(), dto.getHash());
    if (!isMatch) {
        /*
        1. BadRequest.PasswordMatchDTO.password
        2. BadRequest.password
        3. BadRequest
         */
      errors.rejectValue("password", ErrorCode.BAD_REQUEST.name());
      throw new PasswordIsNotMatchException(errors);
    }
  }
}
