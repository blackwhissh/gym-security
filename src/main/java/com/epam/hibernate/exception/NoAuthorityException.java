package com.epam.hibernate.exception;

public class NoAuthorityException extends RuntimeException {
    public NoAuthorityException(String message) {
        super(message);
    }
}
