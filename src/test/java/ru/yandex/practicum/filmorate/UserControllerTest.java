package ru.yandex.practicum.filmorate;

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
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * UserControllerTest
 *
 * @author Aleksei Smyshliaev
 **/

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @MockBean
    private FilmStorage filmStorage;

    @MockBean
    private UserStorage userStorage;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createUserWithCorrectRequestTest() throws Exception {
        String user = "{\"name\": \"Alex\"," +
                " \"email\" : \"alex@alex.com\"," +
                " \"login\": \"engineer\"," +
                " \"birthday\" : \"1946-08-20\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
               .content(user)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }

    @Test
    public void createUserWithIncorrectLoginRequestTest() throws Exception {
        String user = "{\"name\": \"Alex\"," +
                " \"email\" : \"alex@alex.com\"," +
                " \"login\": \"eng in eer\"," +
                " \"birthday\" : \"1946-08-20\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
               .content(user)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void createUserWithEmptyNameRequestTest() throws Exception {
        String user = "{\"email\" : \"alex@alex.com\"," +
                " \"login\": \"engineer\"," +
                " \"birthday\" : \"1946-08-20\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
               .content(user)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }

    @Test
    public void updateUserWithCorrectRequestTest() throws Exception {
        String user = "{\"name\": \"Alex\"," +
                " \"id\" : 1," +
                " \"email\" : \"alex@alex.com\"," +
                " \"login\": \"doctor\"," +
                " \"birthday\" : \"1946-08-20\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/users")
               .content(user)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }

    @Test
    public void updateUserWithIncorrectIdRequestTest() throws Exception {
        String user = "{\"name\": \"Alex\"," +
                " \"id\" : -1," +
                " \"email\" : \"alex@alex.com\"," +
                " \"login\": \"doctor\"," +
                " \"birthday\" : \"1946-08-20\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/users")
               .content(user)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound());
    }

    @Test
    public void updateUserWithoutIdRequestTest() throws Exception {
        String user = "{\"name\": \"Alex\"," +
                " \"email\" : \"alex@alex.com\"," +
                " \"login\": \"doctor\"," +
                " \"birthday\" : \"1946-08-20\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/users")
               .content(user)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void createUserWithIncorrectEmailRequestTest() throws Exception {
        String user = "{\"name\": \"Alex\"," +
                " \"email\" : \"alex.com\"," +
                " \"login\": \"engineer\"," +
                " \"birthday\" : \"1991-05-15\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
               .content(user)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void createUserWithoutEmailRequestTest() throws Exception {
        String user = "{\"name\": \"Alex\"," +
                " \"login\": \"engineer\"," +
                " \"birthday\" : \"1946-08-20\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
               .content(user)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void createUserWithoutLoginRequestTest() throws Exception {
        String user = "{\"name\": \"Alex\"," +
                " \"email\" : \"alex.com\"," +
                " \"birthday\" : \"1946-08-20\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
               .content(user)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void createUserWithIncorrectBirthdayRequestTest() throws Exception {
        String user = "{\"name\": \"Alex\"," +
                " \"email\" : \"alex.com\"," +
                " \"login\": \"engineer\"," +
                " \"birthday\" : \"2150-12-06\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
               .content(user)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void findAllUsersRequestTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users")
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }
}
