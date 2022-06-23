package com.airjnc;

import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.dto.response.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

public class ModelMapperTest {
    private ModelMapper modelMapper;

    @BeforeEach
    void init() {
        this.modelMapper = new ModelMapper();
    }

    @Test
    void mapTest() {
        SignUpDTO signUpDTO = new SignUpDTO();
        signUpDTO.setId(1L);
        signUpDTO.setPassword("q1w2e3");
        signUpDTO.setName("재남");
        signUpDTO.setGender(UserEntity.Gender.FEMALE);

        UserDTO userDTO = this.modelMapper.map(signUpDTO, UserDTO.class);
        System.out.println(userDTO);
    }

    public enum OverTimeValues {
        THREE_HOUR, FIVE_HOUR, WEEKEND_FOUR_HOUR, WEEKEND_EIGHT_HOUR;
    }
}
