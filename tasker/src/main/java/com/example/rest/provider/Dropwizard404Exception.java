package com.example.rest.provider;

public class Dropwizard404Exception extends Throwable {
    private final int code;

    public Dropwizard404Exception() {
        this(500);
    }

    public Dropwizard404Exception(int code) {
        this(code, "Error while processing the request", null);
    }

    public Dropwizard404Exception(int code, String message) {
        this(code, message, null);
    }

    public Dropwizard404Exception(int code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
