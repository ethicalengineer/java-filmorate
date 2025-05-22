package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public List<User> getUsers() {
        return userStorage.findAll();
    }

    public User getUser(long id) {
        return userStorage.getUserById(id);
    }

    public User createUser(User user) {
        validateUser(user);
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        validateUser(user);
        return userStorage.updateUser(user);
    }

    public List<User> getMutualFriends(long firstUser, long secondUser) {
        Set<Long> mutualFriendsIds = new HashSet<>(userStorage.getUserById(firstUser).getFriends());
        mutualFriendsIds.retainAll(userStorage.getUserById(secondUser).getFriends());

        return mutualFriendsIds
                .stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }

    public void makeFriends(long userId, long friendId) {
        getUser(userId).addFriend(friendId);
        getUser(friendId).addFriend(userId);
    }

    public void removeFriend(long userId, long exFriendId) {
        getUser(userId).removeFriend(getUser(exFriendId).getId());
        getUser(exFriendId).removeFriend(getUser(userId).getId());
    }

    public List<User> getUserFriends(long userId) {
        return userStorage.getUserById(userId).getFriends()
                .stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
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
