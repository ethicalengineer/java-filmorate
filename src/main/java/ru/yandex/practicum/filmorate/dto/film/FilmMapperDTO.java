package ru.yandex.practicum.filmorate.dto.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.UserService;

/**
 * FilmMapperDTO
 *
 * @author Aleksei Smyshliaev
 **/

@Component
@RequiredArgsConstructor
public class FilmMapperDTO {
    private final UserService userService;

    public FilmDTO toFilmDTO(Film film) {
        return new FilmDTO(
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getUserLikes().size());
    }
}