package com.airjnc.user.service;

import com.airjnc.common.exception.NotFoundException;
import com.airjnc.common.util.BCryptHashEncrypter;
import com.airjnc.common.util.factory.ErrorsFactory;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.exception.EmailIsDuplicatedException;
import com.airjnc.user.exception.PasswordIsNotMatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor
public class UserCheckService {

  private final UserRepository userRepository;

  public void emailShouldNotBeDuplicated(String email) {
    try {
      userRepository.findByEmail(email);
    } catch (NotFoundException e) {
      return;
    }
        /*
        1. UserCheckService.emailIsDuplicated
        2. UserCheckService
         */
    Errors errors = ErrorsFactory.create("emailIsDuplicated");
    errors.reject(this.getClass().getSimpleName());
    throw new EmailIsDuplicatedException(errors);
  }

  public void passwordShouldBeMatch(String plain, String hash) {
    boolean isMatch = BCryptHashEncrypter.isMatch(plain, hash);
    if (isMatch) {
      return;
    }
        /*
        1. UserCheckService.passwordIsNotMatch
        2. UserCheckService
         */
    Errors errors = ErrorsFactory.create("passwordIsNotMatch");
    errors.reject(this.getClass().getSimpleName());
    throw new PasswordIsNotMatchException(errors);
  }
}
