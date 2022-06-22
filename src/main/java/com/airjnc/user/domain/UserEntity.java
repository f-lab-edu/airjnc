package com.airjnc.user.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class UserEntity {
    public enum Gender {
        FEMALE, MALE
    }

    public Long id;
    public String email;
    public String password;
    public String name;
    public UserEntity.Gender gender;
    public String phoneNumber;
    public String address;
    public boolean isActive;
    public LocalDate birthDate;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public LocalDateTime deletedAt;
}
