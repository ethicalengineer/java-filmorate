package ru.yandex.practicum.filmorate.exception;

/**
 * NotFoundException
 *
 * @author Aleksei Smyshliaev
 **/

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
