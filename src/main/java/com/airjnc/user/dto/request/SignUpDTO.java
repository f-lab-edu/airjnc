package com.airjnc.user.dto.request;

import com.airjnc.user.domain.Gender;
import com.airjnc.user.valid.group.SignUpValid;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"email"})
public class SignUpDTO {

    @NotBlank(message = "{Validation.NotNull}", groups = SignUpValid.class)
    @Email(message = "{Validation.Email}", groups = SignUpValid.class)
    private String email;

    @NotBlank(message = "{Validation.NotNull}", groups = SignUpValid.class)
    @Size(min = 8, max = 12, message = "{Validation.Size}", groups = SignUpValid.class)
    private String password;

    @NotBlank(message = "{Validation.NotNull}", groups = SignUpValid.class)
    private String name;

    @NotNull(message = "{Validation.NotNull}", groups = SignUpValid.class)
    private Gender gender;

    @NotBlank(message = "{Validation.NotNull}", groups = SignUpValid.class)
    private String phoneNumber;

    @NotBlank(message = "{Validation.NotNull}", groups = SignUpValid.class)
    private String address;

    @Past(message = "{Validation.Past}", groups = SignUpValid.class)
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


