package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;

    public List<Film> getFilms() {
        return filmStorage.findAll();
    }

    public Film getFilm(long id) {
        return filmStorage.getFilmById(id);
    }

    public Film createUpdateFilm(Film film) {
        if (film.getId() == null) {
            return filmStorage.addFilm(film);
        }
        return filmStorage.updateFilm(film);
    }

    public List<Film> getPopularFilms(int count) {
        return filmStorage.findAll()
                .stream()
                .sorted(Collections.reverseOrder(Comparator.comparingInt(value -> value.getUserLikes().size())))
                .filter(film -> !film.getUserLikes().isEmpty())
                .limit(count)
                .collect(Collectors.toList());

    }
}
