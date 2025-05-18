package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

/**
 * User
 *
 * @author Aleksei Smyshliaev
 **/

@Data
public class User {
    private Long id;

    @Email(message = "E-mail имеет неверный формат.")
    @NotBlank(message = "E-mail не может быть пустым.")
    private String email;

    @NotBlank(message = "Логин не может быть пустым.")
    private String login;

    private String name;

    @Past(message = "День рождения не может быть в будущем.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private Set<Long> friends;
}
