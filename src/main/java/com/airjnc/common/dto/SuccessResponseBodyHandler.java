package com.airjnc.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SuccessResponseBodyHandler<T> {
    
    private final String message;
    private final T data;

    @Builder
    public SuccessResponseBodyHandler(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
