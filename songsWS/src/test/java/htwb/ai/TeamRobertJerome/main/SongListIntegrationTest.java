package htwb.ai.TeamRobertJerome.main;

import htwb.ai.TeamRobertJerome.main.repository.SongListRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@TestPropertySource(locations = "/test.properties")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SongListIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SongListRepository repository;

    @BeforeEach
    public void setupDB() throws Exception {
        //mockMvc = MockMvcBuilders.standaloneSetup(new SongListRepository(repository)).build();

        String song = "{\"id\":1, \"title\":\"Nothing else Matters\",\"artist\":\"Metallica\",\"label\":\"Sony\",\"released\":1999}";
        MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/songs").header(HttpHeaders.AUTHORIZATION,"52b9f172-8845-476d-ae3a-723068f9c3ae")
                .contentType(MediaType.APPLICATION_JSON)
                .content(song)
        )
                .andExpect(status().isCreated())
                .andReturn();

        String song2 = "{\"id\": 2, \"title\":\"Enter Sandman\",\"artist\":\"Metallica\",\"label\":\"Sony\",\"released\":1999}";
        MvcResult mvcResult2 =  mockMvc.perform(MockMvcRequestBuilders.post("/songs").header(HttpHeaders.AUTHORIZATION,"52b9f172-8845-476d-ae3a-723068f9c3ae")
                .contentType(MediaType.APPLICATION_JSON)
                .content(song2)
        )
                .andExpect(status().isCreated())
                .andReturn();




    }

    @Test
    void postSonglist() throws Exception {
        String songList = "{\"isPrivate\": true, \"name\": \"MaximesPrivateListe\", \"songList\": [ {\"id\":1, \"title\":\"Nothing else Matters\",\"artist\":\"Metallica\",\"label\":\"Sony\",\"released\":1999}, {\"id\": 2, \"title\":\"Enter Sandman\",\"artist\":\"Metallica\",\"label\":\"Sony\",\"released\":1999} ] }";
        MvcResult mvcResult2 =  mockMvc.perform(MockMvcRequestBuilders.post("/songLists").header(HttpHeaders.AUTHORIZATION,"52b9f172-8845-476d-ae3a-723068f9c3ae")
                .contentType(MediaType.APPLICATION_JSON)
                .content(songList)
        )
                .andExpect(status().isCreated())
                .andReturn();

        //String location = mvcResult2.getRequest().getHeader("Location");
        //assertTrue (location.equals("ttp://localhost:8080/songsWS-TeamRobertJerome/rest/songLists/1"));


    }

    @Test
    void ok200getSonglist() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/songLists/songLists?userId=mmuster")
                        .header(HttpHeaders.AUTHORIZATION,"52b9f172-8845-476d-ae3a-723068f9c3ae")
        )
                .andExpect(content().string("{\"id\":1,\"title\":\"Nothing else Matters\",\"artist\":\"Metallica\",\"label\":\"Sony\",\"released\":1999}"))
                .andExpect(status().isOk());

    }



    //... TEST CASES ARE NOT COMPLETE ...//


}
