package com.example.rest.provider;


public class InvalidUserInputException extends Throwable {
    private final int code;

    public InvalidUserInputException() {
        this(400);
    }

    public InvalidUserInputException(int code) {
        this(code, "User input invalid", null);
    }

    public InvalidUserInputException(int code, String message) {
        this(code, message, null);
    }

    public InvalidUserInputException(int code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
