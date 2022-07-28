package com.javamentor.qa.platform.exception;

public class TagNotFoundException extends RuntimeException {

    public TagNotFoundException() {
    }

    public TagNotFoundException(String message) {
        super(message);
    }
}