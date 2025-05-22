package ru.yandex.practicum.filmorate.dto.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

/**
 * UserMapperDTO
 *
 * @author Aleksei Smyshliaev
 **/

@Component
@RequiredArgsConstructor
public class UserMapperDTO {
    private final UserService userService;

    public UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getFriends().stream().map(friend -> userService.getUser(friend).getName()).toList()
        );
    }
}