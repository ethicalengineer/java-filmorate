package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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

    public User createUpdateUser(User user) {
        if (user.getId() == null) {
            return userStorage.addUser(user);
        }
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

    public List<User> getUserFriends(long userId) {
        return userStorage.getUserById(userId).getFriends()
                .stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }
}
