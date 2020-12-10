package htwb.ai.TeamRobertJerome.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import htwb.ai.TeamRobertJerome.model.User;
import htwb.ai.TeamRobertJerome.services.IUserDAO;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping(value="/auth")
public class UserController {

  IUserDAO userDAOImpl;

  @Autowired
  public UserController(IUserDAO userDAO){ userDAOImpl = userDAO; }

  @PostMapping(produces = "text/plain", consumes ="application/json")
  public ResponseEntity<String> authorize(@RequestBody String jsonBody)
    throws IOException {
    try{
      Gson gson = new GsonBuilder().serializeNulls().create();
      User reqUser = gson.fromJson(jsonBody, User.class);
      String password = reqUser.getPassword();
      User user = userDAOImpl.getUserByUserId(reqUser.getUserId());
      if (user.getPassword().equals(password)) {
        UUID uuid = UUID.randomUUID();
        return new ResponseEntity<String> (uuid.toString(), HttpStatus.OK);
      }else{
        return new ResponseEntity<String> ("Declined!!", HttpStatus.UNAUTHORIZED);
      }
    }catch(NotFoundException e){
      return new ResponseEntity<String>("Declined: No such user!",
        HttpStatus.UNAUTHORIZED);
    }catch(JsonSyntaxException e){
      return new ResponseEntity<String>("Bad Json Format!", HttpStatus.BAD_REQUEST);
    }

  }
}
