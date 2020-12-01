package htwb.ai.TeamRobertJerome.controller;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import htwb.ai.TeamRobertJerome.model.Songs;
import htwb.ai.TeamRobertJerome.services.ISongsDAO;
import htwb.ai.TeamRobertJerome.services.SongsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/songs")
public class SongsController {

  ISongsDAO songsDAOImpl;

  @Autowired
  public SongsController(ISongsDAO songDAO){
    songsDAOImpl = songDAO;
  }
    
    /*private UserDAO userDAO = new UserDAO();

  //GET http://localhost:8080/authSpring/rest/authNoDI?userId=aschmidt&password=geheim
    @GetMapping(produces = "text/plain")
    public ResponseEntity<String> authorizeUser(
                        @RequestParam("userId") String userId,
                        @RequestParam("password") String password) 
                                            throws IOException {            
        User user = userDAO.getUserByUserId(userId);
        
        if (user == null || user.getUserId() == null ||
            user.getPassword() == null) {
            return new ResponseEntity<String>("Declined: user nulls??", 
                    HttpStatus.UNAUTHORIZED);
        }
        
        if (user.getUserId().equals(userId) && user.getPassword().equals(password)) {
            return new ResponseEntity<String> ("Welcome!", HttpStatus.OK);
        }
        return new ResponseEntity<String> ("Not Welcome!", HttpStatus.UNAUTHORIZED);
    }*/

    //GET http://localhost:8080/authSpring/rest/authNoDI/1
    @GetMapping(value="/{id}", produces="text/plain")
    public ResponseEntity<String> getUser(@RequestHeader("Accept") String accept,
            @PathVariable (value="id") Integer id) throws Exception {
      Songs song = songsDAOImpl.getSong(id);
      String returnString = new String();

      if(accept.equals("application/json")) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        returnString = gson.toJson(song);
      }

      else if(accept.equals("application/xml")){
        XmlMapper xmlMapper = new XmlMapper();
        returnString = xmlMapper.writeValueAsString(song);
      }
      return new ResponseEntity<String>(returnString, HttpStatus.OK);
    }

    @GetMapping(produces="text/plain")
    public ResponseEntity<String> allSongs(@RequestHeader("Accept") String accept) throws Exception {
      List<Songs> songsList = songsDAOImpl.getAllSongs();
      String returnString = new String();

      if(accept.contains("application/json")){
        Gson gson = new GsonBuilder().serializeNulls().create();
        returnString = gson.toJson(songsList);
      }
      else if(accept.contains("application/xml")){
        XmlMapper xmlMapper = new XmlMapper();
        returnString = xmlMapper.writeValueAsString(songsList);
      }
      System.out.println(returnString);
      return new ResponseEntity<String>(returnString, HttpStatus.OK);
    }

}

