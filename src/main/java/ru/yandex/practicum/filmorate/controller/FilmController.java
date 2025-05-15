package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import java.util.Collection;

/**
 * FilmController
 *
 * @author Aleksei Smyshliaev
 **/

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
    @Autowired
    private final FilmStorage filmStorage;

    @GetMapping
    public Collection<Film> findAll() {
        return filmStorage.findAll().values();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film newFilm) {
        filmStorage.addFilm(newFilm);
        return newFilm;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film newFilm) {
        return filmStorage.updateFilm(newFilm);
    }
}
