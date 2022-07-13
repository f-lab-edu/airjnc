package com.airjnc.user.util.mapper;

import com.airjnc.common.util.ModelMapper;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.CreateDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.testutil.annotation.UnitTest;
import com.testutil.fixture.CreateDTOFixture;
import com.testutil.fixture.UserEntityFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

// 학습 테스트
@UnitTest
class UserEntityMapperTest {
    ModelMapper modelMapper;

    @BeforeEach
    void beforeEach() {
        modelMapper = Mappers.getMapper(ModelMapper.class);
    }

    @Test
    void toUserDTO() {
        //given
        UserEntity userEntity = UserEntityFixture.getBuilder().build();
        //when
        UserDTO result = modelMapper.userEntityToUserDTO(userEntity);
        //then
        assertThat(result.getId()).isEqualTo(userEntity.getId());
        assertThat(result.getName()).isEqualTo(userEntity.getName());
    }

    @Test
    void fromCreateDTO() {
        //given
        CreateDTO createDTO = CreateDTOFixture.getBuilder().build();
        //when
        UserEntity result = modelMapper.createDTOToUserEntity(createDTO);
        //then
        assertThat(result.getEmail()).isEqualTo(createDTO.getEmail());
    }
}
