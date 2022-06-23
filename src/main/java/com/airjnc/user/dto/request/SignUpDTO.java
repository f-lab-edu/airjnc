package com.airjnc.user.dto.request;

import com.airjnc.common.util.BCryptHashEncrypter;
import com.airjnc.user.domain.UserEntity;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
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
}
