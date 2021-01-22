package htwb.ai.TeamRobertJerome.main;

import htwb.ai.TeamRobertJerome.main.repository.SongListRepository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
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

    /*@Autowired
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
    @Order(1)
    void postSonglist() throws Exception {
        String songList = "{\"isPrivate\": true, \"name\": \"MaximesPrivateListe\", \"songList\": [ {\"id\":1, \"title\":\"Nothing else Matters\",\"artist\":\"Metallica\",\"label\":\"Sony\",\"released\":1999}, {\"id\": 2, \"title\":\"Enter Sandman\",\"artist\":\"Metallica\",\"label\":\"Sony\",\"released\":1999} ] }";
        MvcResult mvcResult2 =  mockMvc.perform(MockMvcRequestBuilders.post("/songLists").header(HttpHeaders.AUTHORIZATION,"52b9f172-8845-476d-ae3a-723068f9c3ae")
                .contentType(MediaType.APPLICATION_JSON)
                .content(songList)
        )
                .andExpect(content().string("{\"id\":1,\"name\":\"MaximesPrivateListe\",\"songList\":[{\"id\":1,\"title\":\"Nothing else Matters\",\"artist\":\"Metallica\",\"label\":\"Sony\",\"released\":1999},{\"id\":2,\"title\":\"Enter Sandman\",\"artist\":\"Metallica\",\"label\":\"Sony\",\"released\":1999}],\"private\":true,\"isPrivate\":true}"))

                .andExpect(status().isCreated())
                .andReturn();

        //String location = mvcResult2.getRequest().getContentAsString();
        //System.out.println(location);
        //assertTrue (location.equals("ttp://localhost:8080/songsWS-TeamRobertJerome/rest/songLists/1"));


    }

    @Test
    @Order(2)
    void badRequest400postSonglistNotMatchingTitle() throws Exception {
        String songList = "{\"isPrivate\": true, \"name\": \"MaximesPrivateListe\", \"songList\": [ {\"id\":1, \"title\":\"FALSCHER TITLE\",\"artist\":\"Metallica\",\"label\":\"Sony\",\"released\":1999}, {\"id\": 2, \"title\":\"Enter Sandman\",\"artist\":\"Metallica\",\"label\":\"Sony\",\"released\":1999} ] }";
        MvcResult mvcResult2 =  mockMvc.perform(MockMvcRequestBuilders.post("/songLists").header(HttpHeaders.AUTHORIZATION,"52b9f172-8845-476d-ae3a-723068f9c3ae")
                .contentType(MediaType.APPLICATION_JSON)
                .content(songList)
        )
                .andExpect(status().isBadRequest())
                .andReturn();

        //String location = mvcResult2.getRequest().getContentAsString();
        //System.out.println(location);
        //assertTrue (location.equals("ttp://localhost:8080/songsWS-TeamRobertJerome/rest/songLists/1"));


    }

    @Test
    @Order(2)
    void notFound404postSonglistWrongSongId() throws Exception {
        String songList = "{\"isPrivate\": true, \"name\": \"MaximesPrivateListe\", \"songList\": [ {\"id\":99999, \"title\":\"FALSCHER TITLE\",\"artist\":\"Metallica\",\"label\":\"Sony\",\"released\":1999}, {\"id\": 2, \"title\":\"Enter Sandman\",\"artist\":\"Metallica\",\"label\":\"Sony\",\"released\":1999} ] }";
        MvcResult mvcResult2 =  mockMvc.perform(MockMvcRequestBuilders.post("/songLists").header(HttpHeaders.AUTHORIZATION,"52b9f172-8845-476d-ae3a-723068f9c3ae")
                .contentType(MediaType.APPLICATION_JSON)
                .content(songList)
        )
                .andExpect(status().isBadRequest())
                .andReturn();

        //String location = mvcResult2.getRequest().getContentAsString();
        //System.out.println(location);
        //assertTrue (location.equals("ttp://localhost:8080/songsWS-TeamRobertJerome/rest/songLists/1"));


    }

    @Test
    @Order(2)
    void unauthorized401postSonglistNotValidToken() throws Exception {
        String songList = "{\"isPrivate\": true, \"name\": \"MaximesPrivateListe\", \"songList\": [ {\"id\":99999, \"title\":\"FALSCHER TITLE\",\"artist\":\"Metallica\",\"label\":\"Sony\",\"released\":1999}, {\"id\": 2, \"title\":\"Enter Sandman\",\"artist\":\"Metallica\",\"label\":\"Sony\",\"released\":1999} ] }";
        MvcResult mvcResult2 =  mockMvc.perform(MockMvcRequestBuilders.post("/songLists").header(HttpHeaders.AUTHORIZATION,"notValidToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(songList)
        )
                .andExpect(status().isUnauthorized())
                .andReturn();

        //String location = mvcResult2.getRequest().getContentAsString();
        //System.out.println(location);
        //assertTrue (location.equals("ttp://localhost:8080/songsWS-TeamRobertJerome/rest/songLists/1"));


    }

    @Test
    @Order(2)
    void ok200getSonglist() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/songLists?userId=mmuster")
                        .header(HttpHeaders.AUTHORIZATION,"52b9f172-8845-476d-ae3a-723068f9c3ae")
        )
                .andExpect(content().string("[{\"id\":1,\"name\":\"MaximesPrivateListe\",\"songList\":[{\"id\":1,\"title\":\"Nothing else Matters\",\"artist\":\"Metallica\",\"label\":\"Sony\",\"released\":1999},{\"id\":2,\"title\":\"Enter Sandman\",\"artist\":\"Metallica\",\"label\":\"Sony\",\"released\":1999}],\"private\":true,\"isPrivate\":true}]"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    void ok200getSonglistId() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/songLists/1")
                        .header(HttpHeaders.AUTHORIZATION,"52b9f172-8845-476d-ae3a-723068f9c3ae")
        )
                .andExpect(content().string("{\"id\":1,\"name\":\"MaximesPrivateListe\",\"songList\":[{\"id\":1,\"title\":\"Nothing else Matters\",\"artist\":\"Metallica\",\"label\":\"Sony\",\"released\":1999},{\"id\":2,\"title\":\"Enter Sandman\",\"artist\":\"Metallica\",\"label\":\"Sony\",\"released\":1999}],\"private\":true,\"isPrivate\":true}"))
                .andExpect(status().isOk());
    }



    @Test
    @Order(4)
    void noContentDelete() throws Exception {

        String songList = "{\"isPrivate\": true, \"name\": \"MaximesPrivateListe\", \"songList\": [ {\"id\":1, \"title\":\"Nothing else Matters\",\"artist\":\"Metallica\",\"label\":\"Sony\",\"released\":1999}, {\"id\": 2, \"title\":\"Enter Sandman\",\"artist\":\"Metallica\",\"label\":\"Sony\",\"released\":1999} ] }";
        MvcResult mvcResult2 =  mockMvc.perform(MockMvcRequestBuilders.post("/songLists").header(HttpHeaders.AUTHORIZATION,"52b9f172-8845-476d-ae3a-723068f9c3ae")
                .contentType(MediaType.APPLICATION_JSON)
                .content(songList)
        )
                .andExpect(content().string("{\"id\":2,\"name\":\"MaximesPrivateListe\",\"songList\":[{\"id\":1,\"title\":\"Nothing else Matters\",\"artist\":\"Metallica\",\"label\":\"Sony\",\"released\":1999},{\"id\":2,\"title\":\"Enter Sandman\",\"artist\":\"Metallica\",\"label\":\"Sony\",\"released\":1999}],\"private\":true,\"isPrivate\":true}"))

                .andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.delete("/songLists/2").header(HttpHeaders.AUTHORIZATION,"52b9f172-8845-476d-ae3a-723068f9c3ae")

        )
                .andExpect(status().isNoContent());


    }
*/

    //... TEST CASES ARE NOT COMPLETE ...//


}
