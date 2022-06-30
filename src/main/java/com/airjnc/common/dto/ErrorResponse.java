package com.airjnc.common.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@ToString
public class ErrorResponse {
    private final String exceptionType;
    private final List<String> global;
    private final Map<String, String> field;

    public ErrorResponse(Exception ex, List<String> global, Map<String, String> field) {
        exceptionType = ex.getClass().getSimpleName();
        this.global = global;
        this.field = field;
    }
}
