package com.airjnc.user.dto.request;

import com.airjnc.user.domain.Gender;
import com.airjnc.user.valid.DefaultValidation;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"email"})
public class SignUpDTO {

    @NotBlank(message = "{Validation.NotNull}")
    @Email(message = "{Validation.Email}")
    @Email
    private String email;

    @NotBlank(message = "{Validation.NotNull}")
    @Size(min = 8, max = 12, message = "{Validation.Size}")
    private String password;
    
    @NotBlank(message = "{Validation.NotNull}")
    private String name;

    @NotNull(message = "{Validation.NotNull}")
    private Gender gender;

    @NotBlank(message = "{Validation.NotNull}")
    private String phoneNumber;

    @NotBlank(message = "{Validation.NotNull}")
    private String address;
    
    @Past(message = "생일은 과거여야합니다")
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


