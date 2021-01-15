package TeamRobertJerome.main.controller;

import TeamRobertJerome.main.services.ISongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value="songsWS-TeamRobertJerome/rest/songList")
public class SongListController {
  @Autowired
  ISongListService songListService;


}
