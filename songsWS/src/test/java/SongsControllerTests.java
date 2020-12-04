import com.google.gson.Gson;
import htwb.ai.TeamRobertJerome.controller.SongsController;
import htwb.ai.TeamRobertJerome.model.Songs;
import htwb.ai.TeamRobertJerome.services.SongsDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

public class SongsControllerTests {
  static SongsDAO mockSongsDAO;
  static SongsController songsController;
  static int testIDExistent;
  static int testIDNichtExistent;
  static String acceptJSON;
  static String acceptXML;
  static String acceptDecline;
  static String testJSONBodyGood;
  static String testJSONBodyBad;
  static Songs testSong;
  static String returnStringJSON;


  @BeforeAll
  public static void setUp(){
    mockSongsDAO = Mockito.mock(SongsDAO.class);
    songsController = new SongsController(mockSongsDAO);
    testIDExistent = 1;
    testIDNichtExistent = 1000;
    Gson gson = new Gson();
    acceptJSON = "application/json";
    acceptXML = "application/xml";
    acceptDecline = "text/plain";
    testJSONBodyGood = "{\"title\":\"Wrecking Ball\",\"artist\":\"MILEY" + "CYRUS\",\"label\":\"RCA\",\"released\":2013}";
    testJSONBodyBad = "{\"artist\":\"MILEY\n" + "CYRUS\",\"label\":\"RCA\",\"released\":2013} ";
    returnStringJSON = "{\"id\":0,\"title\":\"Wrecking Ball\",\"artist\":\"MILEY" + "CYRUS\",\"label\":\"RCA\",\"released\":2013}";
    testSong = (gson.fromJson(testJSONBodyGood,Songs.class));

  }

  @Test
  public void getSongTest1GoodJSON() throws Exception {
    when(mockSongsDAO.getSong(Mockito.anyInt())).thenReturn(testSong);
    ResponseEntity<String> testResponse = songsController.getSong(acceptJSON,testIDExistent);
    Assertions.assertTrue(testResponse.getStatusCode().equals(HttpStatus.OK));
    String test = testResponse.getBody();
    Assertions.assertTrue(test.equals(returnStringJSON));
  }
}
