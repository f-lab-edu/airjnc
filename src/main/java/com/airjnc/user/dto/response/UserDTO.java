package com.airjnc.user.dto.response;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
public class UserDTO {
    public enum Gender {
        FEMALE, MALE
    }

    private Long id;
    private String email;
    private String password;
    private String name;
    private UserDTO.Gender gender;
    private String phoneNumber;
    private String address;
    private boolean active;
    private LocalDate birthDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}


