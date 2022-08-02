package com.airjnc.common.util.constant;

import lombok.Getter;

@Getter
public enum SessionKey {
    USER("USER");


    private final String key;

    SessionKey(String key) {
        this.key = key;
    }
}
