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
        mockMvc.perform(MockMvcRequestBuilders.post("/songs").header(HttpHeaders.AUTHORIZATION,"52b9f172-8845-476d-ae3a-723068f9c3ae")
                .contentType(MediaType.APPLICATION_JSON)
                .content(song)
                )
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    void ok200getSongId1() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/songs/1")
                .header(HttpHeaders.AUTHORIZATION,"52b9f172-8845-476d-ae3a-723068f9c3ae")
                )
                .andExpect(content().string("{\"id\":1,\"title\":\"Nothing else Matters\",\"artist\":\"Metallica\",\"label\":\"Sony\",\"released\":1999}"))
                .andExpect(status().isOk());

    }

    @Test
    @Order(3)
    void ok200getSongAll() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/songs")
                        .header(HttpHeaders.AUTHORIZATION,"52b9f172-8845-476d-ae3a-723068f9c3ae")
        )
                .andExpect(content().string("[{\"id\":1,\"title\":\"Nothing else Matters\",\"artist\":\"Metallica\",\"label\":\"Sony\",\"released\":1999}]"))
                .andExpect(status().isOk());

    }


}
