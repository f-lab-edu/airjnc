package com.airjnc.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindPwdResponseDTO {

    private String email;

    private String password;

    @Builder
    public FindPwdResponseDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
