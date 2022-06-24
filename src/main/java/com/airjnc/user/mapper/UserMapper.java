package com.airjnc.user.mapper;


import com.airjnc.user.domain.User;
import com.airjnc.user.dto.UserDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.ERROR, unmappedSourcePolicy = ReportingPolicy.ERROR,
    componentModel = MappingConstants.ComponentModel.SPRING, uses = UserMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    UserDTO toUserDTO(User user);


    User toUser(UserDTO userDTO);


}
