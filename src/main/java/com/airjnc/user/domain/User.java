package com.airjnc.user.domain;

import com.airjnc.common.util.BCryptHashEncoder;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class User {

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

    @Builder
    public User(Long id, String email, String password, String name, Gender gender, String phoneNumber, String address, boolean active, LocalDate birthDate, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.active = active;
        this.birthDate = birthDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    @Builder
    public User(String email, String password, String name, Gender gender, String phoneNumber, String address, LocalDate birthDate) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.birthDate = birthDate;
    }

    public void encodePassword(String password) {
        this.password = BCryptHashEncoder.encode(password);
    }


}


