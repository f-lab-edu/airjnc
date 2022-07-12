package com.airjnc.user.dto.request;


import com.airjnc.user.domain.Gender;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"email"})
public class SignUpDTO {

    @NotBlank(message = "값을 입력해주세요")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "값을 입력해주세요")
    @Size(min = 8, max = 12, message = "비밀번호는 8~12자리를 입력해주세요")
    private String password;
    @NotBlank(message = "값을 입력해주세요")
    private String name;
    @NotNull(message = "값을 입력해주세요")
    private Gender gender;
    @NotBlank(message = "값을 입력해주세요")
    private String phoneNumber;
    @NotBlank(message = "값을 입력해주세요")
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


