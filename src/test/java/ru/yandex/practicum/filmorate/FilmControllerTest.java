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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

/**
 * FilmControllerTest
 *
 * @author Aleksei Smyshliaev
 **/

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class FilmControllerTest {
    @MockBean
    private FilmStorage filmStorage;

    @MockBean
    private UserStorage userStorage;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createFilmWithCorrectRequestTest() throws Exception {
        String film = "{\"name\": \"Matrix\"," +
                " \"description\" : \"Correct Description\"," +
                " \"releaseDate\": \"1991-10-14\"," +
                " \"duration\" : 200}";
        mockMvc.perform(MockMvcRequestBuilders.post("/films")
               .content(film)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void createFilmWithIncorrectDateRequestTest() throws Exception {
        String film = "{\"name\": \"Matrix\"," +
                " \"description\" : \"Correct Description\"," +
                " \"releaseDate\": \"1700-10-14\"," +
                " \"duration\" : 200}";
        mockMvc.perform(MockMvcRequestBuilders.post("/films")
               .content(film)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void updateFilmWithCorrectRequestTest() throws Exception {
        String film = "{\"name\": \"Matrix\"," +
                " \"description\" : \"Another Description\"," +
                " \"id\" : 1," +
                " \"releaseDate\": \"1991-10-14\"," +
                " \"duration\" : 200}";
        mockMvc.perform(MockMvcRequestBuilders.put("/films")
               .content(film)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateFilmWithIncorrectIdRequestTest() throws Exception {
        String film = "{\"name\": \"Matrix\"," +
                " \"description\" : \"Another Description\"," +
                " \"id\" : -1," +
                " \"releaseDate\": \"1991-10-14\"," +
                " \"duration\" : 200}";
        mockMvc.perform(MockMvcRequestBuilders.put("/films")
               .content(film)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void updateFilmWithoutIdRequestTest() throws Exception {
        String film = "{\"name\": \"Matrix\"," +
                " \"description\" : \"Another Description\"," +
                " \"releaseDate\": \"1991-10-14\"," +
                " \"duration\" : 200}";
        mockMvc.perform(MockMvcRequestBuilders.put("/films")
               .content(film)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void createFilmWithIncorrectNameRequestTest() throws Exception {
        String film = "{\"description\" : \"Correct Description\"," +
                " \"releaseDate\": \"1991-10-14\"," +
                " \"duration\" : 200}";
        mockMvc.perform(MockMvcRequestBuilders.post("/films")
               .content(film)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void createFilmWithIncorrectDescriptionRequestTest() throws Exception {
        String film = "{\"name\": \"Matrix\"," +
                " \"description\" : \"12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901" +
                "234567890123456789012345678901234567890\"," +
                " \"releaseDate\": \"1991-10-14\"," +
                " \"duration\" : 200}";
        mockMvc.perform(MockMvcRequestBuilders.post("/films")
               .content(film)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void createFilmWithIncorrectDurationRequestTest() throws Exception {
        String film = "{\"name\": \"Matrix\"," +
                " \"description\" : \"Correct Description\"," +
                " \"releaseDate\": \"1991-10-14\"," +
                " \"duration\" : -1}";
        mockMvc.perform(MockMvcRequestBuilders.post("/films")
               .content(film)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void findAllFilmsRequestTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/films")
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
