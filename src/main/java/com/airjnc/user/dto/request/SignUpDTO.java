package com.airjnc.user.dto.request;

import com.airjnc.common.util.BCryptHashEncrypter;
import com.airjnc.user.domain.UserEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpDTO {
    private Long id;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#!~$%^&-+=()])(?=\\S+$).{8,16}$")
    private String password;
    @NotNull
    private String name;
    @NotNull
    private UserEntity.Gender gender;

    public void changePasswordToHash() {
        this.password = BCryptHashEncrypter.encrypt(this.password);
    }

    @Builder
    public SignUpDTO(Long id, String email, String password, String name, UserEntity.Gender gender) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
    }
}
