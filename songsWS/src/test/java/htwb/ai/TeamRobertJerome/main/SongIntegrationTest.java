package htwb.ai.TeamRobertJerome.main;

import htwb.ai.TeamRobertJerome.main.model.Songs;
import htwb.ai.TeamRobertJerome.main.services.SongsService;
import javassist.NotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(locations = "/test.properties")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SongIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    SongsService songsService;

    @Test
    @Order(1)
    void insertSong() throws Exception {

        String song = "{ \"id\": 1, \"title\": \"Nothing else Matters\", \"artist\": \"Metallica\", \"label\": \"Sony\", \"released\": 1999 }";
        MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/songs").header(HttpHeaders.AUTHORIZATION,"52b9f172-8845-476d-ae3a-723068f9c3ae")
                .contentType(MediaType.APPLICATION_JSON)
                .content(song)
                )
                .andExpect(status().isCreated())
                .andReturn();
        String location = mvcResult.getResponse().getHeader("Location");
        assertTrue(location.equals("http://localhost:8080/songsWS-TeamRobertJerome/rest/songs/1"));
    }

    @Test
    void unauthorized401post() throws Exception {
        String song = "{ \"id\": 1, \"title\": \"Nothing else Matters\", \"artist\": \"Metallica\", \"label\": \"Sony\", \"released\": 1999 }";
        mockMvc.perform(MockMvcRequestBuilders.post("/songs").header(HttpHeaders.AUTHORIZATION,"noValidToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(song)
        )
                .andExpect(status().isUnauthorized());
    }


    @Test
    void badRequst400post() throws Exception {
        String song = "{ \"id\": 1, \"title\": \"Nothing else Mattst\"\"Metallica\", \"label\": \"Sony\", \"released\": 1999 }";
        mockMvc.perform(MockMvcRequestBuilders.post("/songs").header(HttpHeaders.AUTHORIZATION,"52b9f172-8845-476d-ae3a-723068f9c3ae")
                .contentType(MediaType.APPLICATION_JSON)
                .content(song)
        )
                .andExpect(status().isBadRequest());
    }


    @Test

    void ok200getSongId1() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/songs/1")
                .header(HttpHeaders.AUTHORIZATION,"52b9f172-8845-476d-ae3a-723068f9c3ae")
                )
                .andExpect(content().string("{\"id\":1,\"title\":\"Nothing else Matters\",\"artist\":\"Metallica\",\"label\":\"Sony\",\"released\":1999}"))
                .andExpect(status().isOk());

    }

    @Test

    void unauthorizied401getSongId1() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/songs/1")
                        .header(HttpHeaders.AUTHORIZATION,"noValidToken")
        )
                .andExpect(status().isUnauthorized());

    }

    @Test

    void notFound404getSongId1() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/songs/999999")
                        .header(HttpHeaders.AUTHORIZATION,"52b9f172-8845-476d-ae3a-723068f9c3ae")
        )
                 .andExpect(status().isNotFound());

    }

    @Test
    void ok200getSongAll() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/songs")
                        .header(HttpHeaders.AUTHORIZATION,"52b9f172-8845-476d-ae3a-723068f9c3ae")
        )
                .andExpect(content().string("[{\"id\":1,\"title\":\"Nothing else Matters\",\"artist\":\"Metallica\",\"label\":\"Sony\",\"released\":1999}]"))
                .andExpect(status().isOk());

    }

    @Test
    void unauthorized401getSongAll() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/songs")
                        .header(HttpHeaders.AUTHORIZATION,"noValidToken")
        )
                 .andExpect(status().isUnauthorized());

    }

    @Test
    void noContent204DeleteSong() throws Exception {

        String song = "{ \"id\": 2, \"title\": \"Nothing else Matters\", \"artist\": \"Metallica\", \"label\": \"Sony\", \"released\": 1999 }";
       mockMvc.perform(MockMvcRequestBuilders.post("/songs").header(HttpHeaders.AUTHORIZATION,"52b9f172-8845-476d-ae3a-723068f9c3ae")
                .contentType(MediaType.APPLICATION_JSON)
                .content(song)
        )
                .andExpect(status().isCreated());

       mockMvc.perform(MockMvcRequestBuilders.delete("/songs/2").header(HttpHeaders.AUTHORIZATION,"52b9f172-8845-476d-ae3a-723068f9c3ae")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNoContent());

    }


    @Test
    void unauthorized401DeleteSong() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.delete("/songs/2").header(HttpHeaders.AUTHORIZATION,"noValidToken")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isUnauthorized());

    }

    @Test
    void notFound404DeleteSong() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.delete("/songs/9999").header(HttpHeaders.AUTHORIZATION,"52b9f172-8845-476d-ae3a-723068f9c3ae")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound());

    }

    @Test
    void noContent204PutSong() throws Exception {
        String song = "{ \"id\": 1, \"title\": \"CHANGED\", \"artist\": \"Metallica\", \"label\": \"Sony\", \"released\": 1999 }";
        MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.put("/songs/1").header(HttpHeaders.AUTHORIZATION,"52b9f172-8845-476d-ae3a-723068f9c3ae")
                .contentType(MediaType.APPLICATION_JSON)
                .content(song)
        )
                .andExpect(status().isNoContent())
                .andReturn();


    }

    @Test
    void unauthorized401PutSong() throws Exception {
        String song = "{ \"id\": 1, \"title\": \"CHANGED\", \"artist\": \"Metallica\", \"label\": \"Sony\", \"released\": 1999 }";
        mockMvc.perform(MockMvcRequestBuilders.put("/songs/1").header(HttpHeaders.AUTHORIZATION,"noValidToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(song)
        )
                .andExpect(status().isUnauthorized());


    }

    @Test
    void notFound404PutSong() throws Exception {
        String song = "{ \"id\": 9999, \"title\": \"CHANGED\", \"artist\": \"Metallica\", \"label\": \"Sony\", \"released\": 1999 }";
        MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.put("/songs/9999").header(HttpHeaders.AUTHORIZATION,"52b9f172-8845-476d-ae3a-723068f9c3ae")
                .contentType(MediaType.APPLICATION_JSON)
                .content(song)
        )
                .andExpect(status().isNotFound())
                .andReturn();


    }

    @Test
    void badRequest400PutSong() throws Exception {
        String song = "{ \"id\": 1, \"title\": \"CHANGED\", \"artist\": \"Metallica\", \"label\": \"Sony\", \"released\": 1999 }";
        MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.put("/songs/2").header(HttpHeaders.AUTHORIZATION,"52b9f172-8845-476d-ae3a-723068f9c3ae")
                .contentType(MediaType.APPLICATION_JSON)
                .content(song)
        )
                .andExpect(status().isBadRequest())
                .andReturn();


    }


}
