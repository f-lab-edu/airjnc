package com.airjnc.user.service;

import com.airjnc.user.dao.UserRepository;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.CreateDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.util.validator.EmailDuplicateValidator;
import com.airjnc.util.fixture.CreateDTOFixture;
import com.airjnc.util.fixture.UserDTOFixture;
import com.airjnc.util.fixture.UserEntityFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import javax.servlet.http.HttpSession;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    HttpSession httpSession;
    @Mock
    UserRepository userRepository;
    @Mock
    EmailDuplicateValidator emailDuplicateValidator;
    @Spy
    ModelMapper modelMapper;
    @InjectMocks
    UserService userService;

    @Test()
    void userShouldBeCreated() {
        //given
        CreateDTO createDTO = spy(CreateDTOFixture.getBuilder().build());
        UserEntity userEntity = UserEntityFixture.getBuilder().build();
        UserDTO userDTO = UserDTOFixture.getBuilder().build();
        willDoNothing().given(createDTO).changePasswordToHash();
        given(userRepository.save(createDTO)).willReturn(userEntity);
        //when
        UserDTO result = userService.create(createDTO);
        //then
        then(emailDuplicateValidator).should(times(1)).validate(createDTO);
        then(createDTO).should(times(1)).changePasswordToHash();
        then(userRepository).should(times(1)).save(createDTO);
        then(modelMapper).should(times(1)).map(userEntity, UserDTO.class);
        assertThat(result.getId()).isEqualTo(userDTO.getId());
    }

    @Test
    void userShouldBeRemoved() {
        //given
        UserEntity userEntity = UserEntityFixture.getBuilder().build();
        //when
        userService.remove(userEntity.getId());
        //then
        then(userRepository).should(times(1)).remove(userEntity.getId());
    }
}
