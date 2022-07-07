package com.airjnc.user.dto.request;


import com.airjnc.user.domain.Gender;
import com.airjnc.user.dto.response.UserDTO;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class SignUpDTO {
    
    @NotNull
    @Email
    private String email;
    @NotNull
    @Min(8) @Max(12)
    private String password;
    @NotNull
    private String name;
    @NotNull
    private Gender gender;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String address;
    @NotNull
    private LocalDate birthDate;

    @Builder
    public SignUpDTO(String email, String password, String name, Gender gender, String phoneNumber, String address, LocalDate birthDate) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.birthDate = birthDate;
    }
}


