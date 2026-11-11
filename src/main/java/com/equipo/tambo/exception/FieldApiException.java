package com.equipo.tambo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FieldApiException extends RuntimeException {
    private final HttpStatus status;
    private final String field;

    public FieldApiException(HttpStatus status, String field, String message) {
        super(message);
        this.status = status;
        this.field = field;
    }
}
