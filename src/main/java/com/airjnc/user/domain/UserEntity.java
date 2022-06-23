package com.airjnc.user.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter // ModelMapper의 `map`을 통하여 매핑시킬 경우, 필요하다.
@NoArgsConstructor // ModelMapper의 `map`을 통하여 매핑시킬 경우, 필요하다.
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
