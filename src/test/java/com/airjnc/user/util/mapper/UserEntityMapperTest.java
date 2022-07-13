package com.airjnc.user.util.mapper;

import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.CreateDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.testutil.annotation.UnitTest;
import com.testutil.fixture.CreateDTOFixture;
import com.testutil.fixture.UserDTOFixture;
import com.testutil.fixture.UserEntityFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

// 학습 테스트
@UnitTest
class UserEntityMapperTest {
    UserEntityMapper userEntityMapper;

    @BeforeEach
    void beforeEach() {
        userEntityMapper = Mappers.getMapper(UserEntityMapper.class);
    }

    @Test
    void toUserDTO() {
        //given
        UserEntity userEntity = UserEntityFixture.getBuilder().build();
        //when
        UserDTO result = userEntityMapper.toUserDTO(userEntity);
        //then
        assertThat(result.getId()).isEqualTo(userEntity.getId());
        assertThat(result.getName()).isEqualTo(userEntity.getName());
    }

    @Test
    void fromCreateDTO() {
        //given
        CreateDTO createDTO = CreateDTOFixture.getBuilder().build();
        //when
        UserEntity result = userEntityMapper.fromCreateDTO(createDTO);
        //then
        assertThat(result.getEmail()).isEqualTo(createDTO.getEmail());
    }
}
