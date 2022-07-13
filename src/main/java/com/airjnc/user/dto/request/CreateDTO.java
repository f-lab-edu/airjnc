package com.airjnc.user.dto.request;

import com.airjnc.common.util.BCryptHashEncrypter;
import com.airjnc.user.domain.Gender;
import com.airjnc.user.util.Regex;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateDTO {
    private Long id;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Pattern(regexp = Regex.Password.format)
    private String password;
    @NotNull
    private String name;
    @NotNull
    private Gender gender;

    public void changePasswordToHash() {
        password = BCryptHashEncrypter.encrypt(this.password);
    }

    @Builder
    public CreateDTO(Long id, String email, String password, String name, Gender gender) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
    }
}
