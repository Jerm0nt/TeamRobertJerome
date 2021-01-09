package TeamRobertJerome.main.controller;

import TeamRobertJerome.main.model.Songs;
import TeamRobertJerome.main.services.ISongsDAO;
import TeamRobertJerome.main.services.SongsDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;

@RestController
@RequestMapping(value="/songs")
public class SongsController {

  ISongsDAO songsDAOImpl = new SongsDAO();

  /*@Autowired
  public SongsController(ISongsDAO songDAO){
    songsDAOImpl = songDAO;
  } */

    @GetMapping(value="/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Songs> getSong(@RequestHeader("Accept") String accept,
                                          @PathVariable(value="id") Integer id) {
      Songs song;
      try{
        song = songsDAOImpl.getSong(id);
      }
      catch(Exception e){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      return new ResponseEntity<>(song, HttpStatus.OK);
    }

    @GetMapping(produces = {"application/json", "application/xml"})
    public ResponseEntity<List<Songs>> allSongs(@RequestHeader("Accept") String accept) throws JsonProcessingException {
      List<Songs> songsList = null;
      try {
        songsList = songsDAOImpl.getAllSongs();
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      return new ResponseEntity<>(songsList, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
     public ResponseEntity postSong(@RequestBody String jsonBody) {
      Gson gson = new GsonBuilder().serializeNulls().create();
      try {
        Songs song = gson.fromJson(jsonBody, Songs.class);
        int id = songsDAOImpl.postSong(song);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Location", "http://localhost:8080/songsWS_war/rest/songs/"+String.valueOf(id));
        return new ResponseEntity(headers, HttpStatus.CREATED);
      } catch (Exception e) {
        e.printStackTrace();
        return new ResponseEntity((HttpStatus.BAD_REQUEST));
      }
    }


    @PutMapping(value="/{id}", consumes ="application/json")
  public ResponseEntity putSong(@RequestBody String jsonBody, @PathVariable (value = "id") Integer id) {
      try {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Songs song = gson.fromJson(jsonBody, Songs.class);
        songsDAOImpl.putSong(id,song);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
      } catch (NotFoundException e) {
        e.printStackTrace();
        return new ResponseEntity(HttpStatus.NOT_FOUND);
      } catch (InvalidParameterException e) {
        e.printStackTrace();
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
      }catch (JsonSyntaxException e){
        e.printStackTrace();
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
      }
    }

    @DeleteMapping(value="/{id}")
  public ResponseEntity deleteSong(@PathVariable (value="id") Integer id) {
      try {
        songsDAOImpl.deleteSong(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
      } catch (NotFoundException e) {
        e.printStackTrace();
        return new ResponseEntity(HttpStatus.NOT_FOUND);
      }
    }
}

