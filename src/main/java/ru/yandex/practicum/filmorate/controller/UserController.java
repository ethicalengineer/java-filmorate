package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * UserController
 *
 * @author Aleksei Smyshliaev
 **/

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> users = new HashMap<>();
    private long userId = 0;

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User newUser) {
        validateUser(newUser);
        newUser.setId(++userId);
        users.put(newUser.getId(), newUser);
        log.info("Пользователь с ID {} успешно добавлен", newUser.getId());
        return newUser;
    }

    @PutMapping
    public User update(@Valid @RequestBody User newUser) {
        if (newUser.getId() == null) {
            throw new ValidationException("Id обновляемого пользователя не задан.");
        }

        if (!users.containsKey(newUser.getId())) {
            throw new NotFoundException("Пользователь с ID " + newUser.getId() + " не найден.");
        } else {
            validateUser(newUser);
            users.put(newUser.getId(), newUser);
            log.info("Пользователь с ID {} успешно обновлен", newUser.getId());
            return newUser;
        }
    }

    private void validateUser(User newUser) {
        if (newUser.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может содержать пробелы.");
        }

        if (newUser.getName() == null || newUser.getName().isEmpty()) {
            newUser.setName(newUser.getLogin());
            log.info("Пустое отображаемое имя. Использован логин.");
        }
    }
}
