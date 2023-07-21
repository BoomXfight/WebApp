package com.andrej.springboot.exception;

public class InvalidNameFormatException extends RuntimeException {
    public InvalidNameFormatException(String message) {
        super(message);
    }
}
