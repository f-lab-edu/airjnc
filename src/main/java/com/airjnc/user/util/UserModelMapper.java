package com.airjnc.user.util;

import com.airjnc.user.dto.response.UserDTO;
import com.airjnc.user.dto.UserSaveDTO;
import com.airjnc.user.domain.UserEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface UserModelMapper {

  UserDTO userEntityToUserDTO(UserEntity userEntity);

  UserEntity saveDTOToUserEntity(UserSaveDTO userSaveDTO);
}
