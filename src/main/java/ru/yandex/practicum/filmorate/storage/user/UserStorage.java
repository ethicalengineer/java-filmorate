package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

public interface UserStorage {
    void addUser(User user);

    void deleteUser(long id);

    User updateUser(User user);

    Map<Long, User> findAll();
}
