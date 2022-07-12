package com.airjnc.common.exception.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ExceptionResponseBody {
    
    private String message;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("errors")
    private List<CustomFieldError> customFieldErrors;

    public void setMessage(String message) {
        this.message = message;
    }
    
    public void setCustomFieldErrors(List<FieldError> fieldErrors) {
        customFieldErrors = new ArrayList<>();
        fieldErrors.forEach(error -> {
            customFieldErrors.add(new CustomFieldError(
                error.getCodes()[0],
                error.getRejectedValue(),
                error.getDefaultMessage()
            ));
        });
    }
    
    @Getter
    public static class CustomFieldError {
        private String field;
        private Object value;
        private String reason;

        public CustomFieldError(String field, Object value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }
    }
}
