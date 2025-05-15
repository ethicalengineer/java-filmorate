package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

/**
 * UserController
 *
 * @author Aleksei Smyshliaev
 **/

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    @Autowired
    private final UserStorage userStorage;

    @GetMapping
    public Collection<User> findAll() {
        return userStorage.findAll().values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User newUser) {
        userStorage.addUser(newUser);
        return newUser;
    }

    @PutMapping
    public User update(@Valid @RequestBody User newUser) {
        return userStorage.updateUser(newUser);
    }
}
