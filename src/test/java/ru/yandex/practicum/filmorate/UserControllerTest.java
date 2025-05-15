package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import static org.mockito.Mockito.when;

/**
 * UserControllerTest
 *
 * @author Aleksei Smyshliaev
 **/

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {
    @MockBean
    private UserStorage userStorage;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    public UserControllerTest() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @Test
    public void createUserWithCorrectRequestTest() throws Exception {
        String message = "{\"name\": \"Alex\"," +
                " \"email\" : \"alex@alex.com\"," +
                " \"login\": \"engineer\"," +
                " \"birthday\" : \"1946-08-20\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
               .content(message)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void createUserWithIncorrectLoginRequestTest() throws Exception {
        String message = "{\"name\": \"Alex\"," +
                " \"email\" : \"alex@alex.com\"," +
                " \"login\": \"eng in eer\"," +
                " \"birthday\" : \"1946-08-20\"}";

        User user = objectMapper.readValue(message, User.class);
        when(userStorage.addUser(user)).thenThrow(ValidationException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
               .content(message)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void createUserWithEmptyNameRequestTest() throws Exception {
        String message = "{\"email\" : \"alex@alex.com\"," +
                " \"login\": \"engineer\"," +
                " \"birthday\" : \"1946-08-20\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
               .content(message)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateUserWithCorrectRequestTest() throws Exception {
        String message = "{\"name\": \"Alex\"," +
                " \"id\" : 1," +
                " \"email\" : \"alex@alex.com\"," +
                " \"login\": \"doctor\"," +
                " \"birthday\" : \"1946-08-20\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/users")
               .content(message)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateUserWithIncorrectIdRequestTest() throws Exception {
        String message = "{\"name\": \"Alex\"," +
                " \"id\" : -1," +
                " \"email\" : \"alex@alex.com\"," +
                " \"login\": \"doctor\"," +
                " \"birthday\" : \"1946-08-20\"}";

        User user = objectMapper.readValue(message, User.class);
        when(userStorage.updateUser(user)).thenThrow(NotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/users")
               .content(message)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void updateUserWithoutIdRequestTest() throws Exception {
        String message = "{\"name\": \"Alex\"," +
                " \"email\" : \"alex@alex.com\"," +
                " \"login\": \"doctor\"," +
                " \"birthday\" : \"1946-08-20\"}";

        User user = objectMapper.readValue(message, User.class);
        when(userStorage.updateUser(user)).thenThrow(ValidationException.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/users")
               .content(message)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void createUserWithIncorrectEmailRequestTest() throws Exception {
        String message = "{\"name\": \"Alex\"," +
                " \"email\" : \"alex.com\"," +
                " \"login\": \"engineer\"," +
                " \"birthday\" : \"1991-05-15\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
               .content(message)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void createUserWithoutEmailRequestTest() throws Exception {
        String message = "{\"name\": \"Alex\"," +
                " \"login\": \"engineer\"," +
                " \"birthday\" : \"1946-08-20\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
               .content(message)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void createUserWithoutLoginRequestTest() throws Exception {
        String message = "{\"name\": \"Alex\"," +
                " \"email\" : \"alex.com\"," +
                " \"birthday\" : \"1946-08-20\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
               .content(message)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void createUserWithIncorrectBirthdayRequestTest() throws Exception {
        String message = "{\"name\": \"Alex\"," +
                " \"email\" : \"alex.com\"," +
                " \"login\": \"engineer\"," +
                " \"birthday\" : \"2150-12-06\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
               .content(message)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void findAllUsersRequestTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users")
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
