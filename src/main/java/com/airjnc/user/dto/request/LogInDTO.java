package com.airjnc.user.dto.request;

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
public class LogInDTO {
    @NotNull
    @Email
    private String email;
    @NotNull
    @Pattern(regexp = Regex.Password.format)
    private String password;

    @Builder
    public LogInDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
