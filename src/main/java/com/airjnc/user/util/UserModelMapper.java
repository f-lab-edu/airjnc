package com.airjnc.user.util;

import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UserSaveDto;
import com.airjnc.user.dto.response.UserResp;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface UserModelMapper {

  UserEntity userSaveDtoToUserEntity(UserSaveDto userSaveDto);

  UserResp userEntityToUserDTO(UserEntity userEntity);
}
