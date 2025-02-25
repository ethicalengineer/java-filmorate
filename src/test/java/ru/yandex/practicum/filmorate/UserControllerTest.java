package ru.yandex.practicum.filmorate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * UserControllerTest
 *
 * @author Aleksei Smyshliaev
 **/

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class UserControllerTest {
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
                .andExpect(MockMvcResultMatchers.status().is(200));
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
                .andExpect(MockMvcResultMatchers.status().is(200));
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
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void createUserWithoutEmailRequestTest() throws Exception {
        String user = "{\"name\": \"Alex\"," +
                " \"login\": \"engineer\"," +
                " \"birthday\" : \"1946-08-20\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .content(user)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void createUserWithoutLoginRequestTest() throws Exception {
        String user = "{\"name\": \"Alex\"," +
                " \"email\" : \"alex.com\"," +
                " \"birthday\" : \"1946-08-20\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .content(user)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
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
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void findAllUsersRequestTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}
