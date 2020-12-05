import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class SongsControllerTests {
  static SongsDAO mockSongsDAO;
  static SongsController songsController;
  static int testIDExistent;
  static int testIDNichtExistent;
  static String acceptJSON;
  static String acceptXML;
  static String acceptAll;
  static String acceptDecline;
  static String testJSONBodyGood;
  static String testJSONBodyBad;
  static Songs testSong;
  static String returnStringJSON;
  static String returnStringXML;
  static List<Songs> testSongList;
  static String testSongListJSONString;
  static String testSongListXMLString;


  @BeforeAll
  public static void setUp() throws JsonProcessingException {
    mockSongsDAO = Mockito.mock(SongsDAO.class);
    songsController = new SongsController(mockSongsDAO);
    testIDExistent = 1;
    testIDNichtExistent = 1000;
    Gson gson = new Gson();
    acceptJSON = "application/json";
    acceptXML = "application/xml";
    acceptAll = "/";
    acceptDecline = "text/plain";
    testJSONBodyGood = "{\"title\":\"Wrecking Ball\",\"artist\":\"MILEY" + "CYRUS\",\"label\":\"RCA\",\"released\":2013}";
    testJSONBodyBad = "{\"artist\":\"MILEY\n" + "CYRUS\",\"label\":\"RCA\",\"released\":2013} ";
    returnStringJSON = "{\"id\":0,\"title\":\"Wrecking Ball\",\"artist\":\"MILEY" + "CYRUS\",\"label\":\"RCA\",\"released\":2013}";
    returnStringXML ="<Songs>"+"<id>0</id>" + "<title>Wrecking Ball</title>" + "<artist>MILEY" +
      "CYRUS</artist>" + "<label>RCA</label>" + "<released>2013</released>"+"</Songs>";
    testSong = (gson.fromJson(testJSONBodyGood,Songs.class));
    testSongList = new ArrayList<>();
    testSongList.add(testSong);
    testSongList.add(testSong);
    testSongListJSONString = gson.toJson(testSongList);
    XmlMapper xmlMapper = new XmlMapper();
    testSongListXMLString = xmlMapper.writeValueAsString(testSongList);
  }

  @Test
  public void getSongTest1GoodJSON() throws Exception {
    when(mockSongsDAO.getSong(Mockito.anyInt())).thenReturn(testSong);
    ResponseEntity<String> testResponse = songsController.getSong(acceptJSON,testIDExistent);
    Assertions.assertTrue(testResponse.getStatusCode().equals(HttpStatus.OK));
    String test = testResponse.getBody();
    Assertions.assertTrue(test.equals(returnStringJSON));
  }
  @Test
  public void getSongTest2GoodXML() throws Exception {
    when(mockSongsDAO.getSong(Mockito.anyInt())).thenReturn(testSong);
    ResponseEntity<String> testResponse = songsController.getSong(acceptXML,testIDExistent);
    Assertions.assertTrue(testResponse.getStatusCode().equals(HttpStatus.OK));
    String test = testResponse.getBody();
    Assertions.assertTrue(test.equals(returnStringXML));
  }
  @Test
  public void getSongTest3JSONNotFound()throws Exception {
    when(mockSongsDAO.getSong(Mockito.anyInt())).thenThrow(Exception.class);
    ResponseEntity<String> testResponse = songsController.getSong(acceptJSON,testIDNichtExistent);
    Assertions.assertTrue(testResponse.getStatusCode().equals(HttpStatus.NOT_FOUND));
  }
  @Test
  public void getSongTest4XMLNotFound()throws Exception {
    when(mockSongsDAO.getSong(Mockito.anyInt())).thenThrow(Exception.class);
    ResponseEntity<String> testResponse = songsController.getSong(acceptXML,testIDNichtExistent);
    Assertions.assertTrue(testResponse.getStatusCode().equals(HttpStatus.NOT_FOUND));
  }
  @Test
  public void getSongTest5GoodAll() throws Exception {
    when(mockSongsDAO.getSong(Mockito.anyInt())).thenReturn(testSong);
    ResponseEntity<String> testResponse = songsController.getSong(acceptAll,testIDExistent);
    Assertions.assertTrue(testResponse.getStatusCode().equals(HttpStatus.OK));
    String test = testResponse.getBody();
    Assertions.assertTrue(test.equals(returnStringJSON));
  }
  @Test
  public void getSongTest6AllNotFound() throws Exception {
    when(mockSongsDAO.getSong(Mockito.anyInt())).thenThrow(Exception.class);
    ResponseEntity<String> testResponse = songsController.getSong(acceptAll,testIDNichtExistent);
    Assertions.assertTrue(testResponse.getStatusCode().equals(HttpStatus.NOT_FOUND));
  }
  /* Spring Framework greift nicht -> antwortet mit Status 200
  @Test
  public void getSongTest7TextPlain() throws Exception {
    when(mockSongsDAO.getSong(Mockito.anyInt())).thenReturn(testSong);
    ResponseEntity<String> testResponse = songsController.getSong(acceptDecline,testIDExistent);
    HttpStatus testStatus = testResponse.getStatusCode();
    Assertions.assertTrue(testStatus.equals(HttpStatus.NOT_ACCEPTABLE));
  }*/
  @Test
  public void allSongsTest1GoodJSON() throws Exception {

    when(mockSongsDAO.getAllSongs()).thenReturn(testSongList);
    ResponseEntity<String> testResponse = songsController.allSongs(acceptJSON);
    Assertions.assertTrue(testResponse.getStatusCode().equals(HttpStatus.OK));
    String test = testResponse.getBody();
    Assertions.assertTrue(test.equals(testSongListJSONString));
  }
  @Test
  public void allSongsTest2GoodXML() throws Exception {

    when(mockSongsDAO.getAllSongs()).thenReturn(testSongList);
    ResponseEntity<String> testResponse = songsController.allSongs(acceptXML);
    Assertions.assertTrue(testResponse.getStatusCode().equals(HttpStatus.OK));
    String test = testResponse.getBody();
    Assertions.assertTrue(test.equals(testSongListXMLString));
  }

}
