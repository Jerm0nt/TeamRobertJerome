package TeamRobertJerome.main.controller;

import TeamRobertJerome.main.model.SongList;
import TeamRobertJerome.main.services.ISongListService;
import TeamRobertJerome.main.services.IUserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value="songsWS-TeamRobertJerome/rest/songList")
public class SongListController {
  @Autowired
  ISongListService songListService;

  @Autowired
  IUserService userService;

  @GetMapping(value="/{id}", produces = {"application/json", "application/xml"})
  public ResponseEntity getSongList(@PathVariable(value="id") Integer id,
                                    @RequestHeader(name = "Authorization", required = false) String token) {
    if(!userService.isTokenValid(token) || token==null){
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    SongList songList;
    try{
      songList = songListService.getSongList(id);
    }catch (Exception e){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    try{
      if(!songList.getOwner().getUserId().equals(userService.getUserByToken(token).getUserId())){
        return new ResponseEntity(HttpStatus.FORBIDDEN);
      }
    }catch(Exception e){
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(songList, HttpStatus.OK);
  }
  @PostMapping(consumes = "application/json")
  public ResponseEntity postSongList(@RequestBody String jsonBody,
                                     @RequestHeader(name="Authorization", required = false) String token){
    if(!userService.isTokenValid(token)|| token==null){
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    Gson gson = new GsonBuilder().serializeNulls().create();
    try{
      SongList songList = gson.fromJson(jsonBody, SongList.class);
      Gson gsonTest = new Gson();
      String testString = gsonTest.toJson(songList);
      System.out.println(testString);
      int id = songListService.postSongList(songList, token);
      HttpHeaders headers = new HttpHeaders();
      headers.set("Location", "http://localhost:8080/songsWS-TeamRobertJerome/rest/songLists/"+String.valueOf(id));
      return new ResponseEntity(headers, HttpStatus.CREATED);
    }catch (Exception e){
      e.printStackTrace();
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
  }
}
