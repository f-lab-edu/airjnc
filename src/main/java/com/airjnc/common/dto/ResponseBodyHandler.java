package com.airjnc.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseBodyHandler<T> {
    
    String message;
    T data;

    @Builder
    public ResponseBodyHandler(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
