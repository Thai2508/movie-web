package com.identity.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_EXISTED(1001,"User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1002,"User not existed", HttpStatus.BAD_REQUEST),
    USERNAME_SIZE(1003,"Username must be at least {min} character", HttpStatus.BAD_REQUEST),
    PASSWORD_SIZE(1004,"Password must be at least {min} character", HttpStatus.BAD_REQUEST),
    USERNAME_AUTH(1005,"No found Username", HttpStatus.NOT_FOUND),
    PASSWORD_AUTH(1006,"Wrong Password", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007,"You don't have permission", HttpStatus.FORBIDDEN),
    UNAUTHENTICATED(1008,"UnAuthentication", HttpStatus.UNAUTHORIZED),
    PERMISSION_NOT_EXISTED(1009,"Permission not existed", HttpStatus.BAD_REQUEST),
    INVALID_DOB(1010,"Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    NOT_INITIALIZE(1011,"Unable to initialize Profile", HttpStatus.BAD_REQUEST),
    WRONG_OTP(1012,"Wrong Otp", HttpStatus.BAD_REQUEST),
    EMAIL_TIME(1013,"Time off", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1004,"Email existed", HttpStatus.BAD_REQUEST),
    EMAIL_VERIFIED(1001,"Email is verified", HttpStatus.BAD_REQUEST),
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
