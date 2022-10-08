package ru.practicum.service.exception;

public class FalseIdException extends RuntimeException {

    public FalseIdException(final String message) {
        super(message);
    }
}
