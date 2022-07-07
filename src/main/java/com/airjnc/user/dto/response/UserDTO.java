package com.airjnc.user.dto.response;


import com.airjnc.user.domain.Gender;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
public class UserDTO {

    private Long id;
    private String email;
    private String password;
    private String name;
    private Gender gender;
    private String phoneNumber;
    private String address;
    private boolean active;
    private LocalDate birthDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}


