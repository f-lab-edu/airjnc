package com.airjnc.user.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
public class User {
    public enum Gender {
        FEMALE, MALE
    }

    private Long id;
    private String email;
    private String password;
    private String name;
    private User.Gender gender;
    private String phoneNumber;
    private String address;
    private boolean active;
    private LocalDate birthDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;


    @Builder
    public User(String email, String password, String name, User.Gender gender, String phoneNumber, String address, LocalDate birthDate) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.birthDate = birthDate;
    }
}


