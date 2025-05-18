package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

/**
 * Film
 *
 * @author Aleksei Smyshliaev
 **/

@Data
public class Film {
    private Long id;

    @NotBlank(message = "Имя фильма не может быть пустым.")
    private String name;

    @Size(max = 200, message = "Описание должно быть не больше 200 символов.")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @Positive(message = "Длительность должна быть больше нуля.")
    private int duration;

    private Set<Long> userLikes;
}
