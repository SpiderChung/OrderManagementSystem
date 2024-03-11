package ru.schung.order.exception;

import org.webjars.NotFoundException;

public class NoOrdersException extends NotFoundException {
    public NoOrdersException(String message) {
        super(message);
    }
}
