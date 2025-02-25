package ru.yandex.practicum.filmorate.exception;

/**
 * ValidationException
 *
 * @author Aleksei Smyshliaev
 **/

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
