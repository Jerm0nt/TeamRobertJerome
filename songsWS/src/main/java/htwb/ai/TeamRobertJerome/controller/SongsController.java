package htwb.ai.TeamRobertJerome.controller;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import htwb.ai.TeamRobertJerome.model.Songs;
import htwb.ai.TeamRobertJerome.services.ISongsDAO;
import htwb.ai.TeamRobertJerome.services.SongsDAO;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidParameterException;
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
    

    @GetMapping(value="/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<String> getSong(@RequestHeader("Accept") String accept,
            @PathVariable (value="id") Integer id) throws Exception {
      Songs song;
      try{
        song = songsDAOImpl.getSong(id);
      }
      catch(Exception e){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      String returnString = new String();

      if(accept.contains("application/json")) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        returnString = gson.toJson(song);
      }

      else if(accept.contains("application/xml")){
        XmlMapper xmlMapper = new XmlMapper();
        returnString = xmlMapper.writeValueAsString(song);
      }
      else {
        Gson gson = new GsonBuilder().serializeNulls().create();
        returnString = gson.toJson(songsList);
      }
      return new ResponseEntity<String>(returnString, HttpStatus.OK);
    }

    @GetMapping(produces = {"application/json", "application/xml"})
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
      else{
        Gson gson = new GsonBuilder().serializeNulls().create();
        returnString = gson.toJson(songsList);
      }

      System.out.println(returnString);
      return new ResponseEntity<String>(returnString, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
     public ResponseEntity postSong(@RequestBody String jsonBody){
      Gson gson = new GsonBuilder().serializeNulls().create();
      try {
        Songs song = gson.fromJson(jsonBody, Songs.class);
        int id = songsDAOImpl.postSong(song);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Location", String.valueOf(id));
        return new ResponseEntity(headers, HttpStatus.CREATED);
      } catch (Exception e) {
        e.printStackTrace();
        return new ResponseEntity((HttpStatus.NOT_ACCEPTABLE));
      }
    }

    @PutMapping(value="/{id}", consumes ="application/json")
  public ResponseEntity putSong(@RequestBody String jsonBody, @PathVariable (value = "id") Integer id){
      Gson gson = new GsonBuilder().serializeNulls().create();
      Songs song = gson.fromJson(jsonBody, Songs.class);
      try {
        songsDAOImpl.putSong(id,song);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
      } catch (NotFoundException e) {
        e.printStackTrace();
        return new ResponseEntity(HttpStatus.NOT_FOUND);
      }
      catch (InvalidParameterException e){
        e.printStackTrace();
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
      }
    }

    @DeleteMapping(value="/{id}")
  public ResponseEntity deleteSong(@PathVariable (value="id") Integer id){
      try {
        songsDAOImpl.deleteSong(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
      } catch (NotFoundException e) {
        e.printStackTrace();
        return new ResponseEntity(HttpStatus.NOT_FOUND);
      }
    }
}

