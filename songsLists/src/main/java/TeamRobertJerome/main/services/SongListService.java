package TeamRobertJerome.main.services;

import TeamRobertJerome.main.model.SongList;
import TeamRobertJerome.main.model.User;
import TeamRobertJerome.main.repository.SongListRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongListService implements ISongListService {
  @Autowired
  IUserService userService;
  @Autowired
  SongListRepository repository;


  @Override
  public int postSongList(SongList songList, String token) throws NotFoundException {
    User owner = userService.getUserByUserToken(token);
    songList.setOwner(owner);
    repository.save(songList);
    return songList.getSongListId();
  }
}
