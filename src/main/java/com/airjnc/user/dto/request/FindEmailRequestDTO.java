package com.airjnc.user.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"name", "phoneNumber"})
public class FindEmailRequestDTO {

    String name;
    String phoneNumber;

    @Builder
    public FindEmailRequestDTO(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
