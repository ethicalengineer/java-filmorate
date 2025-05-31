package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Film
 *
 * @author Aleksei Smyshliaev
 **/

@Slf4j
@Data
public class Film {
    private Long id;

    @NotBlank(message = "Имя фильма не может быть пустым.")
    private String name;

    @Size(max = 200, message = "Описание должно быть не больше 200 символов.")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @Positive(message = "Длительность должна быть больше нуля.")
    private int duration;

    private Set<Long> userLikes = new HashSet<>();

    public void addLike(long userId) {
        if (userLikes.contains(userId)) {
            throw new IllegalStateException("Пользователь с ID " + userId
                    + " уже оценил фильм " + this.getId());
        }

        userLikes.add(userId);
        log.info("Пользователь с ID {} оценил фильм с ID {}", userId, this.getId());
    }

    public void removeLike(long userId) {
        if (!userLikes.contains(userId)) {
            throw new IllegalStateException("Пользователь с ID " + userId
                    + " не ставил оценку фильму " + this.getId());
        }

        userLikes.remove(userId);
        log.info("Пользователь с ID {} удалил оценку фильма с ID {}", userId, this.getId());
    }
}
