import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import htwb.ai.TeamRobertJerome.controller.UserController;
import htwb.ai.TeamRobertJerome.model.User;
import htwb.ai.TeamRobertJerome.services.UserDAO;
import javassist.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.mockito.Mockito.when;

public class UserControllerTests {


  String testJsonGood;
  String testJsonBadFormat;
  String testJsonBadInfo;
  String testJsonBadPassword;
  UserController userController;
  UserDAO mockUserDAO;
  User testUser;
  String goodUserID;
  String goodPassword;
  Gson gson;


  @BeforeEach
  public void setUp(){
    testJsonGood="{\"userId\":\"eschuler\",\"password\":\"pass1234\"}";
    goodUserID = "eschuler";
    goodPassword = "password";
    testJsonBadFormat="das ist bei Gott kein JSON";
    testJsonBadInfo="{\"userId\":\"falscherhase\",\"password\":\"falschespasswort\"}";
    testJsonBadPassword="{\"userId\":\"eschuler\",\"password\":\"wrongpassword\"}";
    mockUserDAO = Mockito.mock(UserDAO.class);
    userController = new UserController(mockUserDAO);
    gson = new GsonBuilder().serializeNulls().create();
    testUser = gson.fromJson(testJsonGood, User.class);

  }

  @Test
  public void authorizeTest1Good() throws NotFoundException, IOException {
    when(mockUserDAO.getUserByUserId(Mockito.anyString())).thenReturn(testUser);
    ResponseEntity<String> testResponse = userController.authorize(testJsonGood);
    Assertions.assertTrue(testResponse.getStatusCode().equals(HttpStatus.OK));
    Assertions.assertTrue(testResponse.getBody()!=null);
  }
  @Test
  public void authorizeTest2BadJson() throws IOException {
    ResponseEntity<String> testResponse = userController.authorize(testJsonBadFormat);
    Assertions.assertTrue(testResponse.getStatusCode().equals(HttpStatus.BAD_REQUEST));
  }
  @Test
  public void authorizeTest3BadUserInfo() throws NotFoundException, IOException {
    when(mockUserDAO.getUserByUserId(Mockito.anyString())).thenThrow(NotFoundException.class);
    ResponseEntity<String> testResponse = userController.authorize(testJsonBadInfo);
    Assertions.assertTrue(testResponse.getStatusCode().equals(HttpStatus.UNAUTHORIZED));
  }
  @Test
  public void authorizeTest4BadPassword() throws NotFoundException, IOException {
    when(mockUserDAO.getUserByUserId(goodUserID)).thenReturn(testUser);
    ResponseEntity<String> testResponse = userController.authorize(testJsonBadPassword);
    Assertions.assertTrue(testResponse.getStatusCode().equals(HttpStatus.UNAUTHORIZED));
  }
}
