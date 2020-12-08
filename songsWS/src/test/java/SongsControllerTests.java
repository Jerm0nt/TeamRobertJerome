import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import htwb.ai.TeamRobertJerome.controller.SongsController;
import htwb.ai.TeamRobertJerome.model.Songs;
import htwb.ai.TeamRobertJerome.services.SongsDAO;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class SongsControllerTests {
  SongsDAO mockSongsDAO;
  SongsController songsController;
  int testIDExistent;
  int testIDNichtExistent;
  String acceptJSON;
  String acceptXML;
  String acceptAll;
  String acceptDecline;
  String testJSONBodyGood;
  String testJSONBodyBadTitle;
  String testJSONBodyFalse;
  Songs testSong;
  String returnStringJSON;
  String returnStringXML;
  List<Songs> testSongList;
  String testSongListJSONString;
  String testSongListXMLString;


  @BeforeEach
  public void setUpTests() throws JsonProcessingException {
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
    testJSONBodyBadTitle = "{\"artist\":\"MILEY\n" + "CYRUS\",\"label\":\"RCA\",\"released\":2013} ";
    testJSONBodyFalse = "ich bin kein JSON-Body, vallah!";
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

  //getSong(int id, String accept)-Tests
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
  @Test
  public void allSongsTest3ExceptionJSON() throws Exception {
    when(mockSongsDAO.getAllSongs()).thenThrow(Exception.class);
    ResponseEntity<String> testResponse = songsController.allSongs(acceptJSON);
    Assertions.assertTrue(testResponse.getStatusCode().equals(HttpStatus.NOT_FOUND));
  }
  @Test
  public void allSongsTest4ExceptionXML() throws Exception {
    when(mockSongsDAO.getAllSongs()).thenThrow(Exception.class);
    ResponseEntity<String> testResponse = songsController.allSongs(acceptXML);
    Assertions.assertTrue(testResponse.getStatusCode().equals(HttpStatus.NOT_FOUND));
  }
  @Test
  public void allSongsTest5GoodAll() throws Exception {

    when(mockSongsDAO.getAllSongs()).thenReturn(testSongList);
    ResponseEntity<String> testResponse = songsController.allSongs(acceptAll);
    Assertions.assertTrue(testResponse.getStatusCode().equals(HttpStatus.OK));
    String test = testResponse.getBody();
    Assertions.assertTrue(test.equals(testSongListJSONString));
  }
  @Test
  public void allSongsTest6ExceptionAll() throws Exception {
    when(mockSongsDAO.getAllSongs()).thenThrow(Exception.class);
    ResponseEntity<String> testResponse = songsController.allSongs(acceptAll);
    Assertions.assertTrue(testResponse.getStatusCode().equals(HttpStatus.NOT_FOUND));
  }

  //postSong(String jsonBody)-Tests
  @Test
  public void postSongTest1Good() throws Exception {
    when(mockSongsDAO.postSong(Mockito.any())).thenReturn(testIDExistent);
    ResponseEntity<String> testResponse = songsController.postSong(testJSONBodyGood);
    Assertions.assertTrue(testResponse.getStatusCode().equals(HttpStatus.CREATED));
    Assertions.assertTrue(testResponse.getHeaders().getLocation().toString().equals("http://localhost:8080/songsWS_war/rest/songs/"+testIDExistent));
  }

  @Test
  public void postSongTest2BadTitle() throws Exception{
    when(mockSongsDAO.postSong(Mockito.any())).thenThrow(Exception.class);
    ResponseEntity<String> testResponse = songsController.postSong(testJSONBodyBadTitle);
    Assertions.assertTrue(testResponse.getStatusCode().equals(HttpStatus.NOT_ACCEPTABLE));
  }

  @Test
  public void postSongTest3BadJson() throws Exception{
    when(mockSongsDAO.postSong(Mockito.any())).thenReturn(testIDExistent);
    ResponseEntity<String> testResponse = songsController.postSong(testJSONBodyFalse);
    Assertions.assertTrue(testResponse.getStatusCode().equals(HttpStatus.NOT_ACCEPTABLE));
  }

  @Test
  public void putSongTest1Good(){
    ResponseEntity<String> testResponse = songsController.putSong(testJSONBodyGood, testIDExistent);
    Assertions.assertTrue(testResponse.getStatusCode().equals(HttpStatus.NO_CONTENT));
  }

  @Test
  public void putSongTest2BadJson(){
    ResponseEntity<String> testResponse = songsController.putSong(testJSONBodyFalse, testIDExistent);
    Assertions.assertTrue(testResponse.getStatusCode().equals(HttpStatus.BAD_REQUEST));
  }

  @Test
  public void putSong3TestBadIdMatching() throws Exception{
    doThrow(InvalidParameterException.class).when(mockSongsDAO).putSong(Mockito.anyInt(),Mockito.any());
    ResponseEntity<String> testResponse = songsController.putSong(returnStringJSON, testIDExistent);
    String testStatus = testResponse.getStatusCode().toString();
    Assertions.assertTrue(testResponse.getStatusCode().equals(HttpStatus.BAD_REQUEST));
  }

  @Test
  public void putsong4TestIDNotFound() throws Exception{
    doThrow(NotFoundException.class).when(mockSongsDAO).putSong(Mockito.anyInt(),Mockito.any());
    ResponseEntity<String> testResponse = songsController.putSong(returnStringJSON, 2000);
    Assertions.assertTrue(testResponse.getStatusCode().equals(HttpStatus.NOT_FOUND));
  }
}
