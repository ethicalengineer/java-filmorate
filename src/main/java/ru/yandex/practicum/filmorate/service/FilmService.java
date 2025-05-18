package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    @Autowired
    private final FilmStorage filmStorage;

    @Autowired
    private final UserStorage userStorage;

    public void addLike(long filmId, long userId) {
        Film film = filmStorage.getFilmById(filmId);
        User user = userStorage.getUserById(userId);
        if (film.getUserLikes().contains(userId)) {
            throw new ValidationException("Пользователь с ID " + userId
                    + " оценил фильм " + film.getName());
        }

        film.getUserLikes().add(user.getId());
        log.info("Пользователь с ID {} оценил фильм с ID {}", userId, filmId);
    }

    public void removeLike(long filmId, long userId) {
        Film film = filmStorage.getFilmById(filmId);
        User user = userStorage.getUserById(userId);
        film.getUserLikes().remove(user.getId());
        log.info("Пользователь с ID {} удалил оценку фильма с ID {}", userId, filmId);
    }

    public List<Film> getPopularFilms(int count) {
        return filmStorage.findAll().values()
                .stream()
                .sorted(Collections.reverseOrder(Comparator.comparingInt(value -> value.getUserLikes().size())))
                .filter(film -> !film.getUserLikes().isEmpty())
                .limit(count)
                .collect(Collectors.toList());

    }
}
