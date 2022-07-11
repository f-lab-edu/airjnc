package com.airjnc.user.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter // ModelMapper의 `map`을 통하여 매핑시킬 경우, 필요하다.
@NoArgsConstructor(access = AccessLevel.PRIVATE) // ModelMapper의 `map`을 통하여 매핑시킬 경우, 필요하다.
public class UserEntity {
    public enum Gender {
        FEMALE, MALE
    }

    private Long id;
    private String email;
    private String password;
    private String name;
    private UserEntity.Gender gender;
    private String phoneNumber;
    private String address;
    private boolean isActive;
    private LocalDate birthDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Builder
    public UserEntity(Long id, String email, String password, String name, Gender gender, String phoneNumber,
                      String address, boolean isActive, LocalDate birthDate, LocalDateTime createdAt,
                      LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.isActive = isActive;
        this.birthDate = birthDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }
}
