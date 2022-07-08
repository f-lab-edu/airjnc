package com.airjnc.user.service;

import com.airjnc.user.domain.User;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.mapper.UserMapper;
import com.airjnc.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import util.UserFixture;

import java.util.HashMap;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Spy
    UserMapper userMapper;

    @InjectMocks
    UserServiceImpl userServiceImpl;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        this.user = UserFixture.getUserBuilder()
            .build();

        this.userDTO = UserFixture.getUserDTOBuilder()
            .build();
    }

    @Test
    @DisplayName("Password 찾을경우 Password만 반환")
    public void whenFindPasswordThenOnlyReturnPassword() {
        //given
        BDDMockito.given(userRepository.selectUserByEmail(userDTO.getEmail()))
            .willReturn(user);

        //when
        UserDTO pwdUserDTO = userServiceImpl.findPasswordByEmail(this.userDTO.getEmail());

        //then
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> pwdUserMap = mapper.convertValue(pwdUserDTO, HashMap.class);
        pwdUserMap.entrySet().stream().filter(k -> !k.getKey().equals("password") && !k.getKey().equals("active"))
            .forEach(v -> Assertions.assertThat(v.getValue()).isNull());
    }
    
//    @Test
//    @DisplayName("없는 이메일로 Password를 찾는 경우")
//    public void whenNotExistsEmailThenNotReturnPassword() {
//        //given
//        Mockito.doReturn()
//    }
    
    
}