package com.airjnc.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
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

    public UserDTO(Long id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }
}


