package com.airjnc.user.service;

import com.airjnc.common.exception.NotFoundException;
import com.airjnc.common.service.HashService;
import com.airjnc.common.util.factory.ErrorsFactory;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.exception.CurEmailEqualNewEmailException;
import com.airjnc.user.exception.EmailIsDuplicatedException;
import com.airjnc.user.exception.PasswordIsNotMatchException;
import com.airjnc.user.exception.UserIsNotDeletedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCheckService {

  private final UserRepository userRepository;

  private final HashService hashService;

  public void curEmailShouldNotEqualNewEmail(String curEmail, String newEmail) {
    if (!curEmail.equals(newEmail)) {
      return;
    }
    throw new CurEmailEqualNewEmailException(
        ErrorsFactory.createAndReject(this.getClass().getSimpleName(), "curEmailEqualNewEmail", new Object[]{newEmail})
    );
  }

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
    throw new EmailIsDuplicatedException(
        ErrorsFactory.createAndReject(this.getClass().getSimpleName(), "emailIsDuplicated"));
  }

  public void passwordShouldBeMatch(String plain, String hash) {
    boolean isMatch = hashService.isMatch(plain, hash);
    if (isMatch) {
      return;
    }
        /*
        1. UserCheckService.passwordIsNotMatch
        2. UserCheckService
         */
    throw new PasswordIsNotMatchException(
        ErrorsFactory.createAndReject(this.getClass().getSimpleName(), "passwordIsNotMatch")
    );
  }

  public void shouldBeDeleted(UserEntity user) {
    if (user.isDeleted()) {
      return;
    }
    throw new UserIsNotDeletedException(
        ErrorsFactory.createAndReject(this.getClass().getSimpleName(), "userIsNotDeleted")
    );
  }
}
