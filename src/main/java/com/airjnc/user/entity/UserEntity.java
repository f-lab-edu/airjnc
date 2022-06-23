package com.airjnc.user.entity;

import com.airjnc.user.dto.UserDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserEntity {

    public enum Gender {
        FEMALE, MALE
    }
    public Long id;
    public String email;
    public String password;
    public String name;
    public UserDTO.Gender gender;
    public String phoneNumber;
    public String address;
    public boolean isActive;
    public LocalDate birthDate;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public LocalDateTime deletedAt;
    
}
