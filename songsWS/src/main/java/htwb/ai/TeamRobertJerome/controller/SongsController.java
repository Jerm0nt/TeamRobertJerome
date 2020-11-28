package htwb.ai.TeamRobertJerome.controller;

import htwb.ai.TeamRobertJerome.model.Songs;
import htwb.ai.TeamRobertJerome.services.SongsDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value="/songs")
public class SongsController {

  SongsDAO songsDAO = new SongsDAO();
    
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
    public ResponseEntity<String> getUser(
            @PathVariable (value="id") Integer id) throws Exception {
      Songs song = songsDAO.getSong(id);

      return new ResponseEntity<String>("Songname: " + song.getId() + " " +song.getTitle() +" " + song.getArtist()
        +" " + song.getLabel() +" " + song.getReleased(), HttpStatus.OK);
    }

}

