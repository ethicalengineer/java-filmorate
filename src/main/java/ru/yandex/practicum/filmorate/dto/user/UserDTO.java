package ru.yandex.practicum.filmorate.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * UserDTO
 *
 * @author Aleksei Smyshliaev
 **/

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private long id;

    private String email;

    private String login;

    private String name;

    private LocalDate birthday;

    private List<String> friends;
}
