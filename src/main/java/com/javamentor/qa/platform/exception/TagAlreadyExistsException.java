package com.javamentor.qa.platform.exception;

public class TagAlreadyExistsException extends RuntimeException {
    public TagAlreadyExistsException() {
    }

    public TagAlreadyExistsException(String message) {
        super(message);
    }
}