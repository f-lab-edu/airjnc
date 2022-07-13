package com.airjnc.user.util.mapper;

import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.request.CreateDTO;
import com.airjnc.user.dto.response.UserDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-13T11:37:35+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.10 (AdoptOpenJDK)"
)
@Component
public class UserEntityMapperImpl implements UserEntityMapper {

    @Override
    public UserDTO toUserDTO(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserDTO.UserDTOBuilder userDTO = UserDTO.builder();

        userDTO.id( userEntity.getId() );
        userDTO.email( userEntity.getEmail() );
        userDTO.name( userEntity.getName() );
        userDTO.gender( userEntity.getGender() );
        userDTO.phoneNumber( userEntity.getPhoneNumber() );
        userDTO.address( userEntity.getAddress() );
        userDTO.birthDate( userEntity.getBirthDate() );

        return userDTO.build();
    }

    @Override
    public UserEntity fromCreateDTO(CreateDTO createDTO) {
        if ( createDTO == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.id( createDTO.getId() );
        userEntity.email( createDTO.getEmail() );
        userEntity.password( createDTO.getPassword() );
        userEntity.name( createDTO.getName() );
        userEntity.gender( createDTO.getGender() );

        return userEntity.build();
    }
}
