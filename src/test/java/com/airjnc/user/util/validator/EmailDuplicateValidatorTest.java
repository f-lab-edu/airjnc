package com.airjnc.user.util.validator;

import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.util.exception.DuplicatedEmailException;
import com.airjnc.util.fixture.UserEntityFixture;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class EmailDuplicateValidatorTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    EmailDuplicateValidator emailDuplicateValidator;
    SignUpDTO signUpDTO;
    @Mock
    BindException bindException;

    @BeforeEach
    void beforeEach() {
        this.signUpDTO = SignUpDTO.builder().build();
    }

    @Test
    @DisplayName("supports 메소드는 SignUpDTO 타입에 대해서 TRUE를 반환해야 한다.")
    void supports() {
        //when
        boolean supports = emailDuplicateValidator.supports(signUpDTO.getClass());
        //then
        assertThat(supports).isTrue();
    }

    @Test
    @DisplayName("중복되지 않는 이메일이라면, 에러를 던지지 않고, rejectValue가 호출되지 않아야 한다.")
    void notDuplicatedEmail() {
        // given
        when(userRepository.findByEmail(this.signUpDTO.getEmail())).thenReturn(Optional.empty());
        //when
        emailDuplicateValidator.validate(signUpDTO, bindException);
        //then
        then(userRepository).should(times(1)).findByEmail(signUpDTO.getEmail());
        then(bindException).should(times(0)).rejectValue("email", "duplicated");
    }

    @Test
    @DisplayName("중복되는 이메일이라면, DuplicatedEmailException을 던지고, rejectValue가 한 번 호출되야 한다.")
    void duplicatedEmail() {
        // given
        when(userRepository.findByEmail(this.signUpDTO.getEmail())).thenReturn(Optional.of(UserEntityFixture.getBuilder().build()));
        //when
        assertThrows(
            DuplicatedEmailException.class,
            () -> emailDuplicateValidator.validate(signUpDTO, bindException)
        );
        //then
        then(userRepository).should(times(1)).findByEmail(signUpDTO.getEmail());
        then(bindException).should(times(1)).rejectValue("email", "duplicated");
    }
}