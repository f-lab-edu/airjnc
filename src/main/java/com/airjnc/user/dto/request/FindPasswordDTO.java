package com.airjnc.user.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindPasswordDTO {
    
    private String email;
    
    private String password;
    
    @Builder
    public FindPasswordDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
}
