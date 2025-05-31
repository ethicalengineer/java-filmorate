package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.dto.film.FilmMapperDTO;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import static org.mockito.Mockito.when;

/**
 * FilmControllerTest
 *
 * @author Aleksei Smyshliaev
 **/

@RunWith(SpringRunner.class)
@WebMvcTest(FilmController.class)
@AutoConfigureMockMvc
public class FilmControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FilmService filmService;

    @MockitoBean
    private FilmMapperDTO mapper;

    private final ObjectMapper objectMapper;

    public FilmControllerTest() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @Test
    public void createFilmWithCorrectRequestTest() throws Exception {
        String message = "{\"name\": \"Matrix\"," +
                " \"description\" : \"Correct Description\"," +
                " \"releaseDate\": \"1991-10-14\"," +
                " \"duration\" : 200}";
        mockMvc.perform(MockMvcRequestBuilders.post("/films")
               .content(message)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void createFilmWithIncorrectDateRequestTest() throws Exception {
        String message = "{\"name\": \"Matrix\"," +
                " \"description\" : \"Correct Description\"," +
                " \"releaseDate\": \"1700-10-14\"," +
                " \"duration\" : 200}";

        Film film = objectMapper.readValue(message, Film.class);
        when(filmService.createFilm(film)).thenThrow(ValidationException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
               .content(message)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void updateFilmWithCorrectRequestTest() throws Exception {
        String message = "{\"name\": \"Matrix\"," +
                " \"description\" : \"Another Description\"," +
                " \"id\" : 1," +
                " \"releaseDate\": \"1991-10-14\"," +
                " \"duration\" : 200}";
        mockMvc.perform(MockMvcRequestBuilders.put("/films")
               .content(message)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateFilmWithIncorrectIdRequestTest() throws Exception {
        String message = "{\"name\": \"Matrix\"," +
                " \"description\" : \"Another Description\"," +
                " \"id\" : -1," +
                " \"releaseDate\": \"1991-10-14\"," +
                " \"duration\" : 200}";

        Film film = objectMapper.readValue(message, Film.class);
        when(filmService.updateFilm(film)).thenThrow(NotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/films")
               .content(message)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void updateFilmWithoutIdRequestTest() throws Exception {
        String message = "{\"name\": \"Matrix\"," +
                " \"description\" : \"Another Description\"," +
                " \"releaseDate\": \"1991-10-14\"," +
                " \"duration\" : 200}";

        Film film = objectMapper.readValue(message, Film.class);
        when(filmService.updateFilm(film)).thenThrow(ValidationException.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/films")
               .content(message)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void createFilmWithIncorrectNameRequestTest() throws Exception {
        String message = "{\"description\" : \"Correct Description\"," +
                " \"releaseDate\": \"1991-10-14\"," +
                " \"duration\" : 200}";
        mockMvc.perform(MockMvcRequestBuilders.post("/films")
               .content(message)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void createFilmWithIncorrectDescriptionRequestTest() throws Exception {
        String message = "{\"name\": \"Matrix\"," +
                " \"description\" : \"12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901" +
                "234567890123456789012345678901234567890\"," +
                " \"releaseDate\": \"1991-10-14\"," +
                " \"duration\" : 200}";
        mockMvc.perform(MockMvcRequestBuilders.post("/films")
               .content(message)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void createFilmWithIncorrectDurationRequestTest() throws Exception {
        String message = "{\"name\": \"Matrix\"," +
                " \"description\" : \"Correct Description\"," +
                " \"releaseDate\": \"1991-10-14\"," +
                " \"duration\" : -1}";
        mockMvc.perform(MockMvcRequestBuilders.post("/films")
               .content(message)
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
