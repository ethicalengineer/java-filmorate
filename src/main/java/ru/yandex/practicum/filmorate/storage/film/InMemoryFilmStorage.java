package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();
    private long filmId = 0;

    @Override
    public Film addFilm(Film film) {
        validateFilm(film);
        film.setId(++filmId);
        films.put(film.getId(), film);
        log.info("Фильм с ID {} успешно добавлен", film.getId());
        return film;
    }

    @Override
    public void deleteFilm(long id) {
        if (films.containsKey(id)) {
            films.remove(id);
            log.info("Фильм с ID {} успешно удален", id);
        }
        log.info("Фильм с ID {} не существует", id);
    }

    @Override
    public Film updateFilm(Film film) {
        if (film.getId() == null) {
            throw new ValidationException("Id обновляемого фильма не задан.");
        }

        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Фильм с ID " + film.getId() + " не найден.");
        } else {
            validateFilm(film);
            films.put(film.getId(), film);
            log.info("Фильм с ID {} успешно обновлен", film.getId());
            return film;
        }
    }

    @Override
    public Map<Long, Film> findAll() {
        return films;
    }

    private void validateFilm(Film newFilm) {
        if (newFilm.getReleaseDate() != null &&
                newFilm.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата не может быть раньше 28.12.1895.");
        }
    }
}
