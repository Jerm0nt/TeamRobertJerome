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
    User owner = userService.getUserByToken(token);
    songList.setUser(owner);
    repository.save(songList);
    return songList.getId();
  }

  @Override
  public SongList getSongList(Integer id) throws NotFoundException {
    try{
      SongList songList = repository.findById(id).get();
      return songList;
    }catch(Exception e){
      throw new NotFoundException("songList nicht gefunden");
    }
  }
}
