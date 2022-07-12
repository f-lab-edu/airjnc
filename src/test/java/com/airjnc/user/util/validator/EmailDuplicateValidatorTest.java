package com.airjnc.user.util.validator;

import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.exception.DuplicatedEmailException;
import com.airjnc.util.fixture.SignUpDTOFixture;
import com.airjnc.util.fixture.UserEntityFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindException;

import java.util.Optional;

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
        signUpDTO = SignUpDTOFixture.getBuilder().build();
    }


    @Test
    void whenEmailOfSignUpDTOisEmptyThenExceptionIsNotThrown() {
        // given
        given(userRepository.findByEmail(signUpDTO.getEmail())).willReturn(Optional.empty());
        //when
        emailDuplicateValidator.validate(signUpDTO);
        //then
        then(userRepository).should(times(1)).findByEmail(signUpDTO.getEmail());
        then(bindException).should(times(0)).rejectValue("email", "duplicated");
    }

    @Test
    void whenEmailOfSignUpDTOisDuplicatedThenExceptionIsThrown() {
        // given
        when(userRepository.findByEmail(signUpDTO.getEmail())).thenReturn(Optional.of(UserEntityFixture.getBuilder().build()));
        //when
        assertThrows(
            DuplicatedEmailException.class,
            () -> emailDuplicateValidator.validate(signUpDTO)
        );
        //then
        then(userRepository).should(times(1)).findByEmail(signUpDTO.getEmail());
    }
}
