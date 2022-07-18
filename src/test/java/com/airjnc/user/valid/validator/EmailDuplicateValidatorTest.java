package com.airjnc.user.valid.validator;

import com.airjnc.common.error.exception.DuplicateException;
import com.airjnc.user.domain.User;
import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import util.UserFixture;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(MockitoExtension.class)
class EmailDuplicateValidatorTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    EmailDuplicateValidator emailDuplicateValidator;

    private User user;
    private SignUpDTO signUpDTO;

    @BeforeEach
    public void setUp() {

        this.user = UserFixture.getUserBuilder()
            .build();

        this.signUpDTO = UserFixture.getSignUpDTOBuilder()
            .build();
    }

    @Test
    @DisplayName("회원가입시 이메일 중복 체크")
    public void whenDuplicateEmailExistsThenCustomException() {
        //given
        BDDMockito.given(userRepository.selectUserByEmail(signUpDTO.getEmail()))
            .willReturn(Optional.of(user));

        // when, then
        assertThatExceptionOfType(DuplicateException.class).isThrownBy(() -> emailDuplicateValidator.validate(signUpDTO));
    }

}