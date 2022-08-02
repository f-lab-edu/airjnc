package com.airjnc.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindEmailResponseDTO {
    
    String email;

    @Builder
    public FindEmailResponseDTO(String email) {
        this.email = email;
    }
}
