package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserService userService;

    public List<Film> getFilms() {
        return filmStorage.findAll();
    }

    public Film getFilm(long id) {
        return filmStorage.getFilmById(id);
    }

    public Film createFilm(Film film) {
        validateFilm(film);
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        validateFilm(film);
        return filmStorage.updateFilm(film);
    }

    public void likeFilm(long filmId, long userId) {
        getFilm(filmId).addLike(userService.getUser(userId).getId());
    }

    public void removeLikeFromFilm(long filmId, long userId) {
        getFilm(filmId).removeLike(userService.getUser(userId).getId());
    }

    public List<Film> getPopularFilms(int count) {
        return filmStorage.findAll()
                .stream()
                .sorted(Collections.reverseOrder(Comparator.comparingInt(value -> value.getUserLikes().size())))
                .filter(film -> !film.getUserLikes().isEmpty())
                .limit(count)
                .collect(Collectors.toList());

    }

    private void validateFilm(Film newFilm) {
        if (newFilm.getReleaseDate() != null &&
                newFilm.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата не может быть раньше 28.12.1895.");
        }
    }
}
