package com.movie.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    NOT_FOUND(1002,"Not found", HttpStatus.NOT_FOUND),
    UNAUTHORIZED(1007,"You don't have permission", HttpStatus.FORBIDDEN),
    UNAUTHENTICATED(1008,"UnAuthentication", HttpStatus.UNAUTHORIZED),

    ;

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
