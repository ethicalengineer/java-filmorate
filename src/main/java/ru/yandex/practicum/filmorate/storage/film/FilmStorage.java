package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film addFilm(Film film);

    void deleteFilm(long id);

    Film updateFilm(Film film);

    Film getFilmById(long id);

    List<Film> findAll();
}
