package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * User
 *
 * @author Aleksei Smyshliaev
 **/

@Slf4j
@Data
public class User {
    private Long id;

    @Email(message = "E-mail имеет неверный формат.")
    @NotBlank(message = "E-mail не может быть пустым.")
    private String email;

    @NotBlank(message = "Логин не может быть пустым.")
    private String login;

    private String name;

    @Past(message = "День рождения не может быть в будущем.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private Set<Long> friends = new HashSet<>();

    public void addFriend(long friendId) {
        if (friends.contains(friendId)) {
            throw new IllegalStateException("Пользователь с ID " + friendId
                    + " уже является другом пользователя с ID " + this.getId());
        }
        friends.add(friendId);
        log.info("Пользователь с ID {} добавил в друзья пользователя с ID {}", this.getId(), friendId);
    }

    public void removeFriend(long friendId) {
        if (!friends.contains(friendId)) {
            throw new IllegalStateException("Пользователь с ID " + friendId
                    + " не является другом пользователя с ID " + this.getId());
        }
        friends.remove(friendId);
        log.info("Пользователь с ID {} удалил из друзей пользователя с ID {}", this.getId(), friendId);
    }
}
