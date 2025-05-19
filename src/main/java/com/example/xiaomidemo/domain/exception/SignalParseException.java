package com.example.xiaomidemo.domain.exception;

public class SignalParseException extends RuntimeException {
    private final String rawData;

    public SignalParseException(String message, String rawData) {
        super(message);
        this.rawData = rawData;
    }
}
