package htwb.ai.TeamRobertJerome.main;


import htwb.ai.TeamRobertJerome.main.controller.SongListController;
import htwb.ai.TeamRobertJerome.main.controller.UserController;
import htwb.ai.TeamRobertJerome.main.model.User;
import htwb.ai.TeamRobertJerome.main.repository.UserRepository;
import htwb.ai.TeamRobertJerome.main.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@TestPropertySource(locations = "/test.properties")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;




    @Test
    void ok200FullMmusterPass1234() throws Exception {
        String userToLogin = "{\"userId\":\"mmuster\",\"password\":\"pass1234\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userToLogin))
                .andExpect(status().isOk());



    }

    @Test
    void ok200FullEschulerPass1234() throws Exception {
        String userToLogin = "{\"userId\":\"eschuler\",\"password\":\"pass1234\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userToLogin))
                .andExpect(status().isOk());



    }

    @Test
    void notAuthorzied401MmusterWrongPass() throws Exception {
        String userToLogin = "{\"userId\":\"mmuster\",\"password\":\"wrongPass\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userToLogin))
                .andExpect(status().isUnauthorized());



    }

    @Test
    void notAuthorzied401WrongUserId() throws Exception {
        String userToLogin = "{\"userId\":\"wrongUserId\",\"password\":\"pass1234\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userToLogin))
                .andExpect(status().isUnauthorized());



    }

    @Test
    void badJson() throws Exception {
        String userToLogin = "{\"userId\":\"wrongUserId\",\"password\"\"pass1234\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userToLogin))
                .andExpect(status().isBadRequest());



    }
}
