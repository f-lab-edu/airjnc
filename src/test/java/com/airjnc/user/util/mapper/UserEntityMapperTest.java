package com.airjnc.user.util.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.CreateDTO;
import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.util.UserModelMapper;
import com.testutil.annotation.UnitTest;
import com.testutil.fixture.CreateDTOFixture;
import com.testutil.testdata.TestUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

// 학습 테스트
@UnitTest
class UserEntityMapperTest {

  UserModelMapper userModelMapper;

  @BeforeEach
  void beforeEach() {
    userModelMapper = Mappers.getMapper(UserModelMapper.class);
  }

  @Test
  void toUserDTO() {
    //given
    UserEntity userEntity = TestUser.getBuilder().build();
    //when
    UserDTO result = userModelMapper.userEntityToUserDTO(userEntity);
    //then
    assertThat(result.getId()).isEqualTo(userEntity.getId());
    assertThat(result.getName()).isEqualTo(userEntity.getName());
  }

  @Test
  void fromCreateDTO() {
    //given
    CreateDTO createDTO = CreateDTOFixture.getBuilder().build();
    //when
    UserEntity result = userModelMapper.createDTOToUserEntity(createDTO);
    //then
    assertThat(result.getEmail()).isEqualTo(createDTO.getEmail());
  }
}
