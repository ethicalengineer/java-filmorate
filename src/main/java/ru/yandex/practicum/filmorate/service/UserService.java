package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private final UserStorage userStorage;

    public void addFriend(long userId, long friendId) {
        if (userStorage.getUserById(userId).getFriends().contains(friendId)) {
            throw new ValidationException("Пользователь с ID " + friendId
                    + " уже является другом пользователя с ID " + userId);
        }

        userStorage.getUserById(userId).getFriends().add(friendId);
        userStorage.getUserById(friendId).getFriends().add(userId);
        log.info("Пользователь с ID {} добавил в друзья пользователя с ID {}", userId, friendId);
    }

    public void removeFriend(long userId, long exFriendId) {
        userStorage.getUserById(userId).getFriends().remove(exFriendId);
        userStorage.getUserById(exFriendId).getFriends().remove(userId);
        log.info("Пользователь с ID {} удалил из друзей пользователя с ID {}", userId, exFriendId);
    }

    public List<User> getMutualFriends(long firstUser, long secondUser) {
        Set<Long> mutualFriendsIds = new HashSet<>(userStorage.getUserById(firstUser).getFriends());
        mutualFriendsIds.retainAll(userStorage.getUserById(secondUser).getFriends());

        return mutualFriendsIds
                .stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }

    public List<User> getUserFriends(long userId) {
        return userStorage.getUserById(userId).getFriends()
                .stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }
}
