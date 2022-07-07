package com.airjnc.user.mapper;

import com.airjnc.user.domain.User;
import com.airjnc.user.dto.request.SignUpDTO;
import com.airjnc.user.dto.response.UserDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-07T13:50:13+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.11 (AdoptOpenJDK)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO.UserDTOBuilder userDTO = UserDTO.builder();

        userDTO.id( user.getId() );
        userDTO.email( user.getEmail() );
        userDTO.password( user.getPassword() );
        userDTO.name( user.getName() );
        userDTO.gender( user.getGender() );
        userDTO.phoneNumber( user.getPhoneNumber() );
        userDTO.address( user.getAddress() );
        userDTO.active( user.isActive() );
        userDTO.birthDate( user.getBirthDate() );
        userDTO.createdAt( user.getCreatedAt() );
        userDTO.updatedAt( user.getUpdatedAt() );
        userDTO.deletedAt( user.getDeletedAt() );

        return userDTO.build();
    }

    @Override
    public User userDTOtoUser(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( userDTO.getId() );
        user.email( userDTO.getEmail() );
        user.password( userDTO.getPassword() );
        user.name( userDTO.getName() );
        user.gender( userDTO.getGender() );
        user.phoneNumber( userDTO.getPhoneNumber() );
        user.address( userDTO.getAddress() );
        user.active( userDTO.isActive() );
        user.birthDate( userDTO.getBirthDate() );
        user.createdAt( userDTO.getCreatedAt() );
        user.updatedAt( userDTO.getUpdatedAt() );
        user.deletedAt( userDTO.getDeletedAt() );

        return user.build();
    }

    @Override
    public User signUpDTOtoUser(SignUpDTO signUpDTO) {
        if ( signUpDTO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( signUpDTO.getEmail() );
        user.password( signUpDTO.getPassword() );
        user.name( signUpDTO.getName() );
        user.gender( signUpDTO.getGender() );
        user.phoneNumber( signUpDTO.getPhoneNumber() );
        user.address( signUpDTO.getAddress() );
        user.birthDate( signUpDTO.getBirthDate() );

        return user.build();
    }
}
