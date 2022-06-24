package com.airjnc.user.mapper;

import com.airjnc.user.domain.User;
import com.airjnc.user.dto.UserDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-06-25T03:10:16+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.11 (AdoptOpenJDK)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO.UserDTOBuilder userDTO = UserDTO.builder();

        userDTO.id( user.getId() );
        userDTO.email( user.getEmail() );
        userDTO.password( user.getPassword() );
        userDTO.name( user.getName() );
        userDTO.gender( genderToGender( user.getGender() ) );
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
    public User toUser(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( userDTO.getId() );
        user.email( userDTO.getEmail() );
        user.password( userDTO.getPassword() );
        user.name( userDTO.getName() );
        user.gender( genderToGender1( userDTO.getGender() ) );
        user.phoneNumber( userDTO.getPhoneNumber() );
        user.address( userDTO.getAddress() );
        user.active( userDTO.isActive() );
        user.birthDate( userDTO.getBirthDate() );
        user.createdAt( userDTO.getCreatedAt() );
        user.updatedAt( userDTO.getUpdatedAt() );
        user.deletedAt( userDTO.getDeletedAt() );

        return user.build();
    }

    protected UserDTO.Gender genderToGender(User.Gender gender) {
        if ( gender == null ) {
            return null;
        }

        UserDTO.Gender gender1;

        switch ( gender ) {
            case FEMALE: gender1 = UserDTO.Gender.FEMALE;
            break;
            case MALE: gender1 = UserDTO.Gender.MALE;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + gender );
        }

        return gender1;
    }

    protected User.Gender genderToGender1(UserDTO.Gender gender) {
        if ( gender == null ) {
            return null;
        }

        User.Gender gender1;

        switch ( gender ) {
            case FEMALE: gender1 = User.Gender.FEMALE;
            break;
            case MALE: gender1 = User.Gender.MALE;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + gender );
        }

        return gender1;
    }
}
