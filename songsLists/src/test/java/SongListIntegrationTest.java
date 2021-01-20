import TeamRobertJerome.main.repository.SongListRepository;


import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import de.htw.ai.kbe.controller.UserController;
import de.htw.ai.kbe.model.User;
import de.htw.ai.kbe.repo.UserRepository;



@SpringBootTest
@TestPropertySource(locations = "/applicaton.properties")
@TestMethodOrder(OrderAnnotation.class)

public class SongListIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private ISongListRepository repository;

    @BeforeEach
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(new SongListRepository(repository)).build();
    }

    //... TEST CASES ARE NOT COMPLETE ...//

    @Test
    public void whenFindingCustomerById_thenCorrect() {

    }

    @Test
    public void whenFindingAllCustomers_thenCorrect() {

    }
}
