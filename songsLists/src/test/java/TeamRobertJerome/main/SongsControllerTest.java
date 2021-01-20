package TeamRobertJerome.main;

import TeamRobertJerome.main.controller.SongsController;
import TeamRobertJerome.main.model.Songs;
import TeamRobertJerome.main.services.ISongsService;
import TeamRobertJerome.main.services.IUserService;
import TeamRobertJerome.main.services.SongsService;
import TeamRobertJerome.main.services.UserService;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class SongsControllerTest {
  //class-objects
  ISongsService mockSongsService;
  IUserService mockUserService;
  SongsController songsController;
  //ids
  int testIDExistent;
  int testIDNichtExistent;
  //location
  String testLocation;
  //token
  String tokenValid;
  String tokenInvalid;
  String tokenEmpty;
  //JSON-bodies
  String testJSONBodyGood;
  String testJSONBodyNoTitle;
  String testJSONBodyBad;
  String returnStringJSON;
  String getReturnStringXML;
  String testSongListJSON;
  String testSongListXML;
  //test-object
  Songs testSong;
  List<Songs> testSongList;

  @BeforeEach
  public void setUpForTests() throws Exception {
    mockSongsService = Mockito.mock(SongsService.class);
    mockUserService = Mockito.mock(UserService.class);
    songsController = new SongsController();
    songsController.setServices(mockSongsService, mockUserService);

    testIDExistent = 1;
    testIDNichtExistent = 1000;

    testLocation = "http://localhost:8080/songsWS-TeamRobertJerome/rest/songs/";

    Gson gson = new Gson();
    XmlMapper xmlMapper = new XmlMapper();

    tokenValid = "valid";
    tokenInvalid = "invalid";
    tokenEmpty = null;

    testJSONBodyGood = "{\"title\":\"Wrecking Ball\",\"artist\":\"MILEY"
                        + "CYRUS\",\"label\":\"RCA\",\"released\":2013}";
    testJSONBodyNoTitle = "{\"artist\":\"MILEY"
                        + "CYRUS\",\"label\":\"RCA\",\"released\":2013}";
    testJSONBodyBad = "Bei Gott kein JSON-Body";
    returnStringJSON = "{\"id\":0,\"title\":\"Wrecking Ball\",\"artist\":\"MILEY"
                        + "CYRUS\",\"label\":\"RCA\",\"released\":2013}";
    getReturnStringXML = "<Songs>"+"<id>0</id>" + "<title>Wrecking Ball</title>" + "<artist>MILEY" +
                          "CYRUS</artist>" + "<label>RCA</label>" + "<released>2013</released>"+"</Songs>";

    testSong = gson.fromJson(testJSONBodyGood, Songs.class);
    testSongList = new ArrayList<>();
    testSongList.add(testSong);
    testSongList.add(testSong);
    testSongListJSON = gson.toJson(testSongList);
    testSongListXML = xmlMapper.writeValueAsString(testSongList);

    when(mockUserService.isTokenValid(tokenValid)).thenReturn(true);
    when(mockUserService.isTokenValid(tokenInvalid)).thenReturn(false);
    when(mockUserService.isTokenValid(tokenEmpty)).thenReturn(false);
    when(mockSongsService.getSong(testIDExistent)).thenReturn(testSong);
    when(mockSongsService.getSong(testIDNichtExistent)).thenThrow(NotFoundException.class);
  }

  //getSongTests
  @Test public void getSongTest1Good(){
    assertTrue(songsController.getSong(testIDExistent, tokenValid).getStatusCode().equals(HttpStatus.OK));
    assertTrue(songsController.getSong(testIDExistent, tokenValid).getBody().getTitle().equals(testSong.getTitle()));
  }
  @Test public void getSongTest2BadId(){
    assertTrue(songsController.getSong(testIDNichtExistent, tokenValid).getStatusCode().equals(HttpStatus.NOT_FOUND));
  }
  @Test public void getSongTest3InvalidToken(){
    assertTrue(songsController.getSong(testIDExistent, tokenInvalid).getStatusCode().equals(HttpStatus.UNAUTHORIZED));
  }
  @Test public void getSongTest4TokenIsNull(){
    assertTrue(songsController.getSong(testIDExistent, tokenEmpty).getStatusCode().equals(HttpStatus.UNAUTHORIZED));
  }
  //getAllSongsTests
  @Test public void allSongsTest1Good() throws NotFoundException {
    when(mockSongsService.findAll()).thenReturn((ArrayList<Songs>) testSongList);
    assertTrue(songsController.allSongs(tokenValid).getStatusCode().equals(HttpStatus.OK));
  }
  @Test public void allSongsTest2NoSongs() throws NotFoundException {
    when(mockSongsService.findAll()).thenThrow(NotFoundException.class);
    assertTrue(songsController.allSongs(tokenValid).getStatusCode().equals(HttpStatus.NOT_FOUND));
  }
  @Test public void allSongsTest3InvalidToken(){
    assertTrue(songsController.allSongs(tokenInvalid).getStatusCode().equals(HttpStatus.UNAUTHORIZED));
  }
  @Test public void allSongsTest4EmptyToken(){
    assertTrue(songsController.allSongs(tokenEmpty).getStatusCode().equals(HttpStatus.UNAUTHORIZED));
  }
  //postSongTests
  @Test public void postSongTest1Good() throws Exception {
    when(mockSongsService.postSong(Mockito.any())).thenReturn(testIDExistent);
    assertTrue(songsController.postSong(testJSONBodyGood, tokenValid).getStatusCode().equals(HttpStatus.CREATED));
    assertTrue(songsController.postSong(testJSONBodyGood, tokenValid).getHeaders()
                .getLocation().toString().equals(testLocation+testIDExistent));
  }
  @Test public void postSongTest2NoTitle() throws Exception {
    when(mockSongsService.postSong(Mockito.any())).thenThrow(Exception.class);
    assertTrue(songsController.postSong(testJSONBodyNoTitle, tokenValid).getStatusCode().equals(HttpStatus.BAD_REQUEST));
  }
  @Test public void postSongTest3BadJson(){
    assertTrue(songsController.postSong(testJSONBodyBad, tokenValid).getStatusCode().equals(HttpStatus.BAD_REQUEST));
  }
  @Test public void postSongTest4InvalidToken(){
    assertTrue(songsController.postSong(testJSONBodyGood, tokenInvalid).getStatusCode().equals(HttpStatus.UNAUTHORIZED));
  }
  @Test public void postSongTest5EmptyToken(){
    assertTrue(songsController.postSong(testJSONBodyGood, tokenEmpty).getStatusCode().equals(HttpStatus.UNAUTHORIZED));
  }
  //putsong
  @Test public void putSongTest1Good(){
    assertTrue(songsController.putSong(returnStringJSON, testIDExistent, tokenValid).getStatusCode()
                .equals(HttpStatus.NO_CONTENT));
  }
  @Test public void putSongTest2BadJson(){
    assertTrue(songsController.putSong(testJSONBodyBad, testIDExistent, tokenValid).getStatusCode()
              .equals(HttpStatus.BAD_REQUEST));
  }
  @Test public void putSongTest3NotMatchingId() throws NotFoundException {
    doThrow(InvalidParameterException.class).when(mockSongsService).putSong(Mockito.anyInt(),Mockito.any());
    assertTrue(songsController.putSong(returnStringJSON, 2, tokenValid).getStatusCode()
              .equals(HttpStatus.BAD_REQUEST));
  }
}
