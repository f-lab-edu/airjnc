package com.airjnc.user.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter // ModelMapper의 `map`을 통하여 매핑시킬 경우, 필요하다.
@NoArgsConstructor(access = AccessLevel.PRIVATE) // ModelMapper의 `map`을 통하여 매핑시킬 경우, 필요하다.
@Builder
// @Builder는 @XArgsConstructor를 사용하지 않거나, 생성자를 따로 만들지 않을 경우에, 모든 필드를 갖는 전체 생성자를 생성해준다.
// 현재 해당 클래스는 @NoArgsContructor를 달아 기본생성자를 따로 만들어줬기 때문에, @Builder 사용시 @AllArgsConsturctor를 달아주어야 정상 작동한다. 
@AllArgsConstructor
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
}
