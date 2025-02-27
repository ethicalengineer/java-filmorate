package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * FilmController
 *
 * @author Aleksei Smyshliaev
 **/

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Long, Film> films = new HashMap<>();
    private long filmId = 0;

    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film newFilm) {
        validateFilm(newFilm);
        newFilm.setId(++filmId);
        films.put(newFilm.getId(), newFilm);
        log.info("Фильм с ID {} успешно добавлен", newFilm.getId());
        return newFilm;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film newFilm) {
        if (newFilm.getId() == null) {
            throw new ValidationException("Id обновляемого фильма не задан.");
        }

        if (!films.containsKey(newFilm.getId())) {
            throw new NotFoundException("Фильм с ID " + newFilm.getId() + " не найден.");
        } else {
            validateFilm(newFilm);
            films.put(newFilm.getId(), newFilm);
            log.info("Фильм с ID {} успешно обновлен", newFilm.getId());
            return newFilm;
        }
    }

    private void validateFilm(Film newFilm) {
        if (newFilm.getReleaseDate() != null &&
                newFilm.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата не может быть раньше 28.12.1895.");
        }
    }
}
