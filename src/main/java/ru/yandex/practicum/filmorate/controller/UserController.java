package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;

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

    @Autowired
    private final UserService userService;

    @GetMapping
    public Collection<User> findAll() {
        return userStorage.findAll().values();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable long id) {
        return userStorage.getUserById(id);
    }

    @GetMapping("/{id}/friends")
    public List<User> getUserFriends(@PathVariable long id) {
        return userService.getUserFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(@PathVariable long id, @PathVariable long otherId) {
        return userService.getMutualFriends(id, otherId);
    }

    @PostMapping
    public User create(@Valid @RequestBody User newUser) {
        userStorage.addUser(newUser);
        return newUser;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void makeFriends(@PathVariable long id, @PathVariable long friendId) {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable long id, @PathVariable long friendId) {
        userService.removeFriend(id, friendId);
    }

    @PutMapping
    public User update(@Valid @RequestBody User newUser) {
        return userStorage.updateUser(newUser);
    }
}
