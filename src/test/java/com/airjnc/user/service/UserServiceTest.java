package com.airjnc.user.service;

import com.airjnc.common.util.validator.ValidatorTemplate;
import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.util.validator.EmailDuplicateValidator;
import com.airjnc.util.fixture.UserDTOFixture;
import com.airjnc.util.fixture.UserEntityFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Spy
    ModelMapper modelMapper; // 라이브러리이므로, 목 메소드를 만들지 않고, 실제 메소드 호출로 테스트진행
    @Mock
    EmailDuplicateValidator emailDuplicateValidator;
    @Mock
    ValidatorTemplate validatorTemplate;
    @InjectMocks
    UserService userService;

    @Test()
    @DisplayName("create")
    void create() {
        //given
        SignUpDTO signUpDTO = mock(SignUpDTO.class);
        UserEntity userEntity = UserEntityFixture.getBuilder().build();
        UserDTO userDTO = UserDTOFixture.getBuilder().build();
        willDoNothing().given(signUpDTO).changePasswordToHash();
        given(userRepository.save(signUpDTO)).willReturn(userEntity);
        //when
        UserDTO result = userService.create(signUpDTO);
        //then
        then(validatorTemplate).should(times(1)).validate(emailDuplicateValidator, signUpDTO);
        then(signUpDTO).should(times(1)).changePasswordToHash();
        then(userRepository).should(times(1)).save(signUpDTO);
        then(modelMapper).should(times(1)).map(userEntity, UserDTO.class);
        assertThat(result.getId()).isEqualTo(userDTO.getId());
    }
}