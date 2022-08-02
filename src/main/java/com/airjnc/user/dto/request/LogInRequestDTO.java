package com.airjnc.user.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"email"})
public class LogInRequestDTO {

    String email;
    String password;

    @Builder
    public LogInRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
