package com.airjnc.user.util;

import com.airjnc.user.domain.UserEntity;
import com.airjnc.user.dto.UserSaveDto;
import com.airjnc.user.dto.response.UserResp;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-01T13:26:52+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.10 (AdoptOpenJDK)"
)
@Component
public class UserModelMapperImpl implements UserModelMapper {

    @Override
    public UserEntity saveDTOToUserEntity(UserSaveDto userSaveDTO) {
        if ( userSaveDTO == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.id( userSaveDTO.getId() );
        userEntity.email( userSaveDTO.getEmail() );
        userEntity.password( userSaveDTO.getPassword() );
        userEntity.name( userSaveDTO.getName() );
        userEntity.gender( userSaveDTO.getGender() );
        userEntity.birthDate( userSaveDTO.getBirthDate() );

        return userEntity.build();
    }

    @Override
    public UserResp userEntityToUserResp(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserResp.UserRespBuilder userResp = UserResp.builder();

        userResp.id( userEntity.getId() );
        userResp.email( userEntity.getEmail() );
        userResp.name( userEntity.getName() );
        userResp.gender( userEntity.getGender() );
        userResp.phoneNumber( userEntity.getPhoneNumber() );
        userResp.address( userEntity.getAddress() );
        userResp.birthDate( userEntity.getBirthDate() );
        userResp.deletedAt( userEntity.getDeletedAt() );

        return userResp.build();
    }
}
