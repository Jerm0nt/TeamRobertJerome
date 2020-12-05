package htwb.ai.TeamRobertJerome.controller;

import htwb.ai.TeamRobertJerome.model.User;
import htwb.ai.TeamRobertJerome.services.IUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value="/auth")
public class UserController {

  IUserDAO userDAOImpl;

  @Autowired
  public UserController(IUserDAO userDAO){ userDAOImpl = userDAO; }

  @PostMapping(produces = "text/plain", consumes ="application/json")
  // authorize eigentlich nicht per GET!!
  public ResponseEntity<String> authorize(
    @RequestParam("userId") String userId,
    @RequestParam("password") String password)
    throws IOException {
    User user = userDAOImpl.getUserByUserId(userId);
    if (user == null || user.getUserId() == null ||
      user.getPassword() == null) {
      return new ResponseEntity<String>("Declined: No such user!",
        HttpStatus.UNAUTHORIZED);
    }

    if (user.getUserId().equals(userId) && user.getPassword().equals(password)) {
      return new ResponseEntity<String> ("Welcome!!", HttpStatus.OK);
    }
    return new ResponseEntity<String> ("Declined!!", HttpStatus.UNAUTHORIZED);
  }
}
