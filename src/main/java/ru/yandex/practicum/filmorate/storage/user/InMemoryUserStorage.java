package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private long userId = 0;

    @Override
    public User addUser(User user) {
        validateUser(user);
        user.setId(++userId);
        user.setFriends(new HashSet<>());
        users.put(user.getId(), user);
        log.info("Пользователь с ID {} успешно добавлен", user.getId());
        return user;
    }

    @Override
    public void deleteUser(long id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("Пользователь с ID " + id + " не найден.");
        }
        users.remove(id);
        log.info("Пользователь с ID {} успешно удален", id);
    }

    @Override
    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("Пользователь с ID " + user.getId() + " не найден.");
        } else {
            validateUser(user);
            users.put(user.getId(), user);
            log.info("Пользователь с ID {} успешно обновлен", user.getId());
            return user;
        }
    }

    @Override
    public List<User> findAll() {
        return users.values().stream().toList();
    }

    @Override
    public User getUserById(long userId) {
        if (!users.containsKey(userId)) {
            throw new NotFoundException("Пользователь с ID " + userId + " не найден.");
        }
        return users.get(userId);
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
