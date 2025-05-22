package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.film.FilmDTO;
import ru.yandex.practicum.filmorate.dto.film.FilmMapperDTO;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

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
    private final FilmService filmService;
    private final FilmMapperDTO mapper;

    @GetMapping
    public List<FilmDTO> getAllFilms() {
        return filmService.getFilms()
                .stream()
                .map(mapper::toFilmDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public FilmDTO getFilmById(@PathVariable long id) {
        return mapper.toFilmDTO(filmService.getFilm(id));
    }

    @GetMapping("/popular")
    public List<FilmDTO> getPopular(@RequestParam(value = "count", defaultValue = "10") int count) {
        return filmService.getPopularFilms(count)
                .stream()
                .map(mapper::toFilmDTO)
                .toList();
    }

    @PostMapping
    public FilmDTO createFilm(@Valid @RequestBody Film film) {
        return mapper.toFilmDTO(filmService.createFilm(film));
    }

    @PutMapping
    public FilmDTO updateFilm(@Valid @RequestBody Film film) {
        if (film.getId() == null) {
            throw new ValidationException("Id обновляемого фильма не задан.");
        }
        return mapper.toFilmDTO(filmService.updateFilm(film));
    }

    @PutMapping("/{id}/like/{userId}")
    public void likeFilm(@PathVariable long id, @PathVariable long userId) {
        filmService.likeFilm(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLikeFromFilm(@PathVariable long id, @PathVariable long userId) {
        filmService.removeLikeFromFilm(id, userId);
    }
}
