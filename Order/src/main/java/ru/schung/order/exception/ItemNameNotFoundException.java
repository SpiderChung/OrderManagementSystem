package ru.schung.order.exception;

import jakarta.persistence.EntityNotFoundException;
import org.webjars.NotFoundException;

public class ItemNameNotFoundException extends EntityNotFoundException {
    public ItemNameNotFoundException(String message) {
        super(message);
    }
}
