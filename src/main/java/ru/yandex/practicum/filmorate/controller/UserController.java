package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.user.UserMapperDTO;
import ru.yandex.practicum.filmorate.dto.user.UserDTO;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

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
    private final UserService userService;
    private final UserMapperDTO mapper;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getUsers().stream().map(mapper::toUserDTO).toList();
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable long id) {
        return mapper.toUserDTO(userService.getUser(id));
    }

    @GetMapping("/{id}/friends")
    public List<UserDTO> getUserFriends(@PathVariable long id) {
        return userService.getUserFriends(id).stream().map(mapper::toUserDTO).toList();
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<UserDTO> getMutualFriends(@PathVariable long id, @PathVariable long otherId) {
        return userService.getMutualFriends(id, otherId).stream().map(mapper::toUserDTO).toList();
    }

    @PostMapping
    public UserDTO createUser(@Valid @RequestBody User user) {
        return mapper.toUserDTO(userService.createUpdateUser(user));
    }

    @PutMapping
    public UserDTO updateUser(@Valid @RequestBody User user) {
        if (user.getId() == null) {
            throw new ValidationException("Id обновляемого пользователя не задан.");
        }
        return mapper.toUserDTO(userService.createUpdateUser(user));
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void makeFriends(@PathVariable long id, @PathVariable long friendId) {
        userService.getUser(id).addFriend(friendId);
        userService.getUser(friendId).addFriend(id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable long id, @PathVariable long friendId) {
        userService.getUser(id).removeFriend(userService.getUser(friendId).getId());
        userService.getUser(friendId).removeFriend(userService.getUser(id).getId());
    }
}
