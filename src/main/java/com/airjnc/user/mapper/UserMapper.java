package com.airjnc.user.mapper;


import com.airjnc.user.domain.User;
import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.dto.response.UserDTO;
import org.mapstruct.*;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.ERROR, unmappedSourcePolicy = ReportingPolicy.ERROR,
    componentModel = MappingConstants.ComponentModel.SPRING, uses = UserMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    UserDTO userToUserDTO(User user);
    
    User userDTOtoUser(UserDTO userDTO);
    
    @Mapping(target="id", ignore = true)
    @Mapping(target="active", ignore = true)
    @Mapping(target="createdAt", ignore = true)
    @Mapping(target="updatedAt", ignore = true)
    @Mapping(target="deletedAt", ignore = true)
    User signupDTOtoUser(SignUpDTO signUpDTO);
}
