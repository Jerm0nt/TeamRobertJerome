package htwb.ai.TeamRobertJerome.main;

import htwb.ai.TeamRobertJerome.main.repository.SongListRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@TestPropertySource(locations = "/test.properties")
@TestMethodOrder(OrderAnnotation.class)
public class SongListIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private SongListRepository repository;

    @BeforeEach
    public void setupMockMvc() {
        //mockMvc = MockMvcBuilders.standaloneSetup(new SongListRepository(repository)).build();
    }

    //... TEST CASES ARE NOT COMPLETE ...//


}
