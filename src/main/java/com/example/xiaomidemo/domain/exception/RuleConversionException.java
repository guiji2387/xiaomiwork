package com.example.xiaomidemo.domain.exception;

public class RuleConversionException extends RuntimeException {
    public RuleConversionException(String message) {
        super(message);
    }
    public RuleConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
