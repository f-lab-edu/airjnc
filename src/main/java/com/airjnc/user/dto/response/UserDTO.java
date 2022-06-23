package com.airjnc.user.dto.response;

import com.airjnc.user.domain.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter // ModelMapper의 `map`을 통하여 매핑시킬 경우, 필요하다.
@NoArgsConstructor // ModelMapper의 `map`을 통하여 매핑시킬 경우, 필요하다.
public class UserDTO {
    @NotNull
    private Long id;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String name;
    @NotNull
    private UserEntity.Gender gender;
    private String phoneNumber;
    private String address;
    private LocalDate birthDate;
}
