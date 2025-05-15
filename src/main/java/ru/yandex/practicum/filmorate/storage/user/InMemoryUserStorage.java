package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
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
        users.put(user.getId(), user);
        log.info("Пользователь с ID {} успешно добавлен", user.getId());
        return user;
    }

    @Override
    public void deleteUser(long id) {
        if (users.containsKey(id)) {
            users.remove(id);
            log.info("Пользователь с ID {} успешно удален", id);
        }
        log.info("Пользователь с ID {} не существует", id);
    }

    @Override
    public User updateUser(User user) {
        if (user.getId() == null) {
            throw new ValidationException("Id обновляемого пользователя не задан.");
        }

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
    public Map<Long, User> findAll() {
        return users;
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
