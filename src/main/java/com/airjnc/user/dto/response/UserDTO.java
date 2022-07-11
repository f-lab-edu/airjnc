package com.airjnc.user.dto.response;

import com.airjnc.user.domain.Gender;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter // ModelMapper의 `map`을 통하여 매핑시킬 경우, 필요하다.
@NoArgsConstructor(access = AccessLevel.PRIVATE) // ModelMapper의 `map`을 통하여 매핑시킬 경우, 필요하다.
public class UserDTO {
    @NotNull
    private Long id;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String name;
    @NotNull
    private Gender gender;
    private String phoneNumber;
    private String address;
    private LocalDate birthDate;

    @Builder
    public UserDTO(Long id, String email, String name, Gender gender, String phoneNumber, String address,
                   LocalDate birthDate) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.birthDate = birthDate;
    }
}
