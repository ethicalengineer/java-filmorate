package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Map;

public interface FilmStorage {
    void addFilm(Film film);

    void deleteFilm(long id);

    Film updateFilm(Film film);

    Map<Long, Film> findAll();
}
