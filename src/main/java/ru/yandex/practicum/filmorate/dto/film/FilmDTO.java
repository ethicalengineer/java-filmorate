package ru.yandex.practicum.filmorate.dto.film;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * FilmDTO
 *
 * @author Aleksei Smyshliaev
 **/

@Getter
@Setter
@AllArgsConstructor
public class FilmDTO {
    private long id;

    private String name;

    private String description;

    private LocalDate releaseDate;

    private int duration;

    private int userLikes;
}
