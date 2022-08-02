package com.airjnc.common.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AuthInfoDTO {

    private Long id;
    private String email;
    private String name;


    @Builder
    public AuthInfoDTO(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

}
