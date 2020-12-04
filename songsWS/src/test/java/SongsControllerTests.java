import htwb.ai.TeamRobertJerome.controller.SongsController;
import htwb.ai.TeamRobertJerome.services.SongsDAO;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;

public class SongsControllerTests {
  SongsDAO mockSongsDAO;
  SongsController songsController;
  int testIDExistent;
  int testIDNichtExistent;
  String acceptAccept;
  String acceptDecline;
  String testJSONBodyGood;
  String testJSONBodyBad;


  @BeforeAll
  public void setUp(){
    mockSongsDAO = Mockito.mock(SongsDAO.class);
    songsController = new SongsController(mockSongsDAO);
    testIDExistent = 1;
    testIDNichtExistent = 1000;
    testJSONBodyGood = "{\"title\":\"Wrecking Ball\",\"artist\":\"MILEY\n" + "CYRUS\",\"label\":\"RCA\",\"released\":2013} ";
    testJSONBodyBad = "{\"artist\":\"MILEY\n" + "CYRUS\",\"label\":\"RCA\",\"released\":2013} ";
  }
}
